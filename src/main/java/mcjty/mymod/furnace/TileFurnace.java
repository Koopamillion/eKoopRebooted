package mcjty.mymod.furnace;

import mcjty.mymod.tools.MyEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;

public class TileFurnace extends TileEntity implements ITickable {


    public static final int INPUT_SLOTS = 3;
    public static final int OUTPUT_SLOTS = 3;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_POWER = 100000;
    public static final int RF_PER_TICK = 20;
    public static final int RF_PER_TICK_INPUT = 100;
    //static var means it is the same for all acelerating furnaces so only use it for something that wont change per furnace (ie slots)


    //start time
    private  final float furnaceSmeltTimeMax = 100;
    //max end time actually idk anymore
    private  final float furnaceSmeltTimeMin = 5;
    public float time = furnaceSmeltTimeMax;

    public  float guiTime = 100;
    private float progressRemaining = 0;
    private float scale = 0;
    private float increaseWithoutCompound = 0.0005f;
    private float increaseWithCompound = 0.025f;
    private float clientProgress = -1;
    //public int progressInt = 0;

    private boolean changeScale = true;
    //should it accelerate faster and faster? Basically if this is false, its more balanced
    private boolean compound = false;


    @Override
    public void update() {
        if (!world.isRemote) {
            if (energyStorage.getEnergyStored() < RF_PER_TICK){
                return;
            }




            guiTime = time;
            if (progressRemaining > 0) {
                energyStorage.consumePower(RF_PER_TICK);



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
