package koopamillion.mymod.furnace;

import koopamillion.mymod.sound.SoundFurnace;
import koopamillion.mymod.tools.MyEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static koopamillion.mymod.furnace.FurnaceState.WORKING;

public class TileFurnace extends TileEntity implements ITickable {


    public static final int INPUT_SLOTS = 3;
    public static final int OUTPUT_SLOTS = 3;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_POWER = 100000;
    public float RF_PER_TICK = 20;
    public static final int RF_PER_TICK_INPUT = 250;
    //static var means it is the same for all acelerating furnaces so only use it for something that wont change per furnace (ie slots)


    //start time
    private  final float furnaceSmeltTimeMax = 100;
    //max end time actually idk anymore
    private  final float furnaceSmeltTimeMin = 5;
    public float time = furnaceSmeltTimeMax;

    public  float guiTime = 100;
    private float progressRemaining = 0;
    private float scale = 0;

    private int soundCounter = 0;
    private float increaseWithoutCompound = 0.0005f;
    private float increaseWithCompound = 0.025f;
    private float clientProgress = -1;
    private FurnaceState state = FurnaceState.OFF;
    private int clientEnergy = -1;

    private boolean changeScale = true;
    //should it accelerate faster and faster? Basically if this is false, its more balanced
    private boolean compound = false;


    @Override
    public void update() {
        if (!world.isRemote) {
                if (shouldRun() == true){
                if (energyStorage.getEnergyStored() < Math.round(RF_PER_TICK)){

                    return;
                }

                if (progressRemaining > 0) {
                    setState(FurnaceState.WORKING);
                    energyStorage.consumePower(Math.round(RF_PER_TICK));



                    progressRemaining --;
                    //if the algorithm wont be below 0.1 then set the time to the algorithm
                    if  (furnaceSmeltTimeMax - ((furnaceSmeltTimeMax - furnaceSmeltTimeMin)* scale) > 0.1){
                        time = furnaceSmeltTimeMax - ((furnaceSmeltTimeMax - furnaceSmeltTimeMin)* scale);

                    }else{
                        //if it is below 0.1 then set it to 0.1f
                        time = 0.1f;
                        //and change the boolean so the scale dosent keep increasing and the function dosent need to be called multiple times
                        changeScale = false;
                    }
                    // System.out.println(time);
                    //if there is no ticks left to remove, GO AND SMELT
                    if (progressRemaining <= 0) {

                        attemptSmelt();
                    }

                    markDirty();
                } else {

                    //no ticks left? must have started, GO AND START THE SMELTING PROCESS LOL
                    startSmelt();

                }
            }else{
                    if(changeScale==false){
                        changeScale=true;
                        scale = scale - 0.001f;
                    }else if (scale > 0){
                        scale = scale - 0.001f;
                        if  (furnaceSmeltTimeMax - ((furnaceSmeltTimeMax - furnaceSmeltTimeMin)* scale) <= 100){
                            time = furnaceSmeltTimeMax - ((furnaceSmeltTimeMax - furnaceSmeltTimeMin)* scale);

                        }else{

                            time = 100f;


                        }
                    }else{
                        scale = 0;
                    }

                    setState(FurnaceState.OFF);
                }

                if(getState()==WORKING && soundCounter == 0){

                    soundCounter = 34;
                    BlockPos pos = getPos();
                    World worldIn = getWorld();

                    worldIn.playSound(null,(double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundFurnace.furnaceActive, SoundCategory.BLOCKS, 2.0f, 1.0F);
                //    worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundFurnace.furnaceActive, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
                }
                if(soundCounter > 0){
                    soundCounter--;
                }

            }


        }






    private boolean insertOutput(ItemStack output, boolean simulate) {
        for (int i = 0; i < OUTPUT_SLOTS; i++) {

            ItemStack remaining = outputHandler.insertItem(i, output, simulate);
            if (remaining.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldRun(){
        for (int i = 0; i < INPUT_SLOTS; i++){
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if(!result.isEmpty()){  //does input slot got something in?
                if (insertOutput(result.copy(), true)) { //is there a free output slot
                    if(!(energyStorage.getEnergyStored() < Math.round(RF_PER_TICK))){   //is there enough energy
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    return false;
                }
            }
        }


        return false;

        }

    private void startSmelt() {
        for (int i = 0; i < INPUT_SLOTS; i++) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                if (insertOutput(result.copy(), true)) {
                    //if changescale is enable then increase scale
                    if (changeScale == true){
                        if(compound == false){
                            scale = scale + (increaseWithoutCompound * time);

                        }else{
                            scale = scale + increaseWithCompound;

                        }


                    }
                    //set progressremaining to time
                    progressRemaining = time;
                    guiTime = time;
                    markDirty();
                    }

                break;
                }


            }
        }


    private void attemptSmelt() {
        for (int i = 0; i < INPUT_SLOTS; i++) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                // This copy is very important!(
                if (insertOutput(result.copy(), false)) {
                    inputHandler.extractItem(i, 1, false);
                    break;

                }
            }
        }
    }


    public float getProgressRemaining() {
        return progressRemaining;
    }

    public void setProgressRemaining(float progressRemaining){
        this.progressRemaining = progressRemaining;
    }

    public float getTime(){
        return time;
    }

    //seperate gui time var for gui only

    public float getGuiTime(){
        return guiTime;
    }



    public void setGuiTime(float guiTime){
        this.guiTime = guiTime;
    }

    public float getClientProgress() {
        return clientProgress;
    }

    public void setClientProgress(float clientProgress) {
        this.clientProgress = clientProgress;
    }

    public int getClientEnergy() {
        return clientEnergy;
    }

    public void setClientEnergy(int clientEnergy) {
        this.clientEnergy = clientEnergy;
    }

    public int getEnergy(){
        return energyStorage.getEnergyStored();
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setInteger("state", state.ordinal());
        nbtTag.setInteger("energy", energyStorage.getEnergyStored());
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }


    //client
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        int stateIndex = packet.getNbtCompound().getInteger("state");
        clientEnergy = packet.getNbtCompound().getInteger("energy");
        energyStorage.setEnergy(packet.getNbtCompound().getInteger("energy"));
        if (world.isRemote && stateIndex != state.ordinal()) {
            state = FurnaceState.VALUES[stateIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }

    public void setState(FurnaceState state) {
        if (this.state != state) {
            this.state = state;
            markDirty();
            IBlockState blockState = world.getBlockState(pos);
            getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
        }
    }

    public FurnaceState getState() {
        return state;
    }

    //-------------------------------------------------------------------------------------------------------------


    // This item handler will hold our three input slots
    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);
            return !result.isEmpty();
        }


        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileFurnace.this.markDirty();
        }
    };


    // This item handler will hold our three output slots
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileFurnace.this.markDirty();
        }
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }
    };

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    //-------------------------------------------------------------------------------------------------------------

    //reads that saved stuff
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if (compound.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
        progressRemaining = compound.getFloat("progressRemaining");
        energyStorage.setEnergy(compound.getInteger("energy"));
        time = compound.getFloat("time");
        scale = compound.getFloat("scale");
    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setFloat("progressRemaining", progressRemaining);
        compound.setFloat("scale", scale);
        compound.setFloat("time", time);
        compound.setInteger("energy", energyStorage.getEnergyStored());
        return compound;
    }

    //-------------------------------------------------------------------------------------------------------------

    private MyEnergyStorage energyStorage = new MyEnergyStorage(MAX_POWER, RF_PER_TICK_INPUT);

    //-------------------------------------------------------------------------------------------------------------

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if (capability == CapabilityEnergy.ENERGY){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            } else if (facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            } else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
            }
        }
        if (capability == CapabilityEnergy.ENERGY){
            return  CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        return super.getCapability(capability, facing);
    }
}
