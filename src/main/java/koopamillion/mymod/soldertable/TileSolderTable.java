package koopamillion.mymod.soldertable;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.ModLiquids;
import koopamillion.mymod.crafting.SolderManager;
import koopamillion.mymod.crafting.SolderRecipe;
import koopamillion.mymod.tools.IRestorableTileEntity;
import koopamillion.mymod.tools.MyEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileSolderTable extends TileEntity implements ITickable, IRestorableTileEntity {

    public static final int INPUT_SLOTS = 10;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_POWER = 10000;
    public int RF_PER_TICK = 2;
    private int amountsmooth = 0;
    public static final int RF_PER_TICK_INPUT = 250;

    //static var means it is the same for all acelerating furnaces so only use it for something that wont change per furnace (ie slots)



    private int heat = 0;
    public static final int MAX_HEAT = 100000;
    private float progressRemaining = 0;

    private int soundCounter = 0;

    EnumFacing facing = EnumFacing.NORTH;

    private FluidTank tank = new FluidTank(4000);
    private FluidTank tank2 = new FluidTank(4000);

    private int clientFluidAmount = tank.getFluidAmount();
    private float clientProgress = -1;
private Fluid fluidtype = null;

    private NBTTagCompound clienttag = new NBTTagCompound();

    private int clientheat = -1;
    private int clientEnergy = -1;

    private int drain = 0;



    @Override
    public void update() {


        if (!world.isRemote) {
            controlHeat();
       //     System.out.println(heat);
            if (energyStorage.getEnergyStored() < Math.round(RF_PER_TICK)){

                return;
            }

            if (progressRemaining > 0) {

                progressRemaining--;
                energyStorage.consumePower(RF_PER_TICK);
                if (progressRemaining <= 0) {
                    attemptSmelt();
                }
                markDirty();
            } else {
                startSmelt();
            }
            fluidMake();
            int tanker = tank.getFluidAmount();

           // System.out.println(tanker);


        }


    }

/*    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }*/






    public EnumFacing getFacing(){
        return this.facing;
    }


    public IBlockState getBlockState(){
        return world.getBlockState(pos);

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
            ItemStack result = getResult(inputHandler.getStackInSlot(i));
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
            ItemStack result = getResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                if (insertOutput(result.copy(), true)) {
                    progressRemaining = 40;
                    markDirty();
                }

                break;
            }


        }
    }

    private void controlHeat(){
        if(heat < 0){
            heat = 0;
        }
        if(heat > MAX_HEAT){
            heat = MAX_HEAT;
        }
        if(energyStorage.getEnergyStored() >= this.RF_PER_TICK){
            if(heat < MAX_HEAT){
                energyStorage.consumePower(RF_PER_TICK);
                heat = heat + 10;
            }else if(heat >= MAX_HEAT){
                energyStorage.consumePower(RF_PER_TICK / 2);
            }

        }else if(heat > 0){
            heat = (heat - 1);
        }
    }

    private void attemptSmelt() {
        for (int i = 0; i < INPUT_SLOTS; i++) {
            ItemStack result = getResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                // This copy is very important!(
                if (insertOutput(result.copy(), false)) {
                    for(int l = 0; l < 9; l++){
                        inputHandler.extractItem(l, 1, false);

                    }
                    tank.drain(drain, true);

                    break;

                }
            }
        }
    }
    private void fluidMake(){
        ItemStack stack = inputHandler.getStackInSlot(9);
        if(tank.getFluid() != null) {
             if(stack.getItem() == ModItems.itemSolder && tank.getFluidAmount() < tank.getCapacity() && amountsmooth <= 0 && this.getEnergy() >= 5 && this.heat >= ModItems.itemSolder.getMeltingPoint() * 200 && tank.getFluid().getFluid() == ModLiquids.solderfluid){
            amountsmooth = 20;
            fluidtype = ModLiquids.solderfluid;
            inputHandler.extractItem(9, 1, false);
              }

            if (stack.getItem() == ModItems.enderingot && tank.getFluidAmount() < tank.getCapacity() && amountsmooth <= 0 && this.getEnergy() >= 5 && this.heat >= 250 * 200 && tank.getFluid().getFluid() == ModLiquids.enderfluid) {
                amountsmooth = 20;
                fluidtype = ModLiquids.enderfluid;
                inputHandler.extractItem(9, 1, false);
            }
        }else {
            if(stack.getItem() == ModItems.itemSolder && tank.getFluidAmount() < tank.getCapacity() && amountsmooth <= 0 && this.getEnergy() >= 5 && this.heat >= ModItems.itemSolder.getMeltingPoint() * 200  ){
                amountsmooth = 20;
                fluidtype = ModLiquids.solderfluid;
                inputHandler.extractItem(9, 1, false);
            }

            if (stack.getItem() == ModItems.enderingot && tank.getFluidAmount() < tank.getCapacity() && amountsmooth <= 0 && this.getEnergy() >= 5 && this.heat >= 250 * 200 ) {
                amountsmooth = 20;
                fluidtype = ModLiquids.enderfluid;
                inputHandler.extractItem(9, 1, false);
            }
        }

        IFluidHandler handler = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
        if(tank.getFluid() != null){


        if(amountsmooth > 0 && this.getEnergy() >= 5 && tank.getFluid().getFluid() == fluidtype){
            handler.fill(new FluidStack(fluidtype, 5),true);
            amountsmooth --;
            this.energyStorage.consumePower(5);
        }}else{if(amountsmooth > 0 && this.getEnergy() >= 5){
            handler.fill(new FluidStack(fluidtype, 5),true);
            amountsmooth --;
            this.energyStorage.consumePower(5);
        }}

    }




    private ItemStack getResult(ItemStack stack ){
        SolderRecipe recipe = SolderManager.getRecipe(inputHandler.getStackInSlot(0), inputHandler.getStackInSlot(1), inputHandler.getStackInSlot(2), inputHandler.getStackInSlot(3), inputHandler.getStackInSlot(4), inputHandler.getStackInSlot(5), inputHandler.getStackInSlot(6), inputHandler.getStackInSlot(7), inputHandler.getStackInSlot(8), tank.getFluid(), new ItemStack(ModItems.puresand, 4));
        if(recipe != null){
            drain = recipe.getInput10Amount();
        }
        if (recipe!= null&&tank.getFluidAmount() >= recipe.getInput10Amount() && tank.getFluid().getFluid() == recipe.getInput10().getFluid()){
            return  recipe.getOutput();

        }
        return ItemStack.EMPTY;
    }



    public float getProgressRemaining() {
        return progressRemaining;
    }

    public void setProgressRemaining(float progressRemaining){
        this.progressRemaining = progressRemaining;
    }

    public float getClientProgress() {
        return clientProgress;
    }

    public void setClientProgress(float clientProgress) {
        this.clientProgress = clientProgress;
    }

    public NBTTagCompound getClienttag() {
        return clienttag;
    }

    public void setClienttag(NBTTagCompound clienttag) {
        this.clienttag = clienttag;
    }

    public int getClientEnergy() {
        return clientEnergy;
    }
    public int getCapacity(){
        return tank.getCapacity();

    }    public void setClientEnergy(int clientEnergy) {
        this.clientEnergy = clientEnergy;
    }

    public void setClientFluidAmount(int clientFluidAmount) {
        this.clientFluidAmount = clientFluidAmount;
    }
    public int getClientFluidAmount(){
        return clientFluidAmount;
    }

    public int getFluidAmount(){
        return tank.getFluidAmount();
    }
    public int getHeat(){return heat;}
    public void setHeat(int heat){this.heat = heat;}
    public int getClientheat(){return clientheat;}

    public void setClientheat(int clientheat){this.clientheat = clientheat;}

    public int getTestInt(){return 342;}


    public FluidTank getTank(){
        return tank;

    }

    public int getEnergy(){
        return energyStorage.getEnergyStored();
    }



    @Override
    public NBTTagCompound getUpdateTag () {
        NBTTagCompound nbtTag = super.getUpdateTag();
        NBTTagCompound tankNBT = new NBTTagCompound();
        tank.writeToNBT(tankNBT);
        nbtTag.setTag("tank", tankNBT);
        nbtTag.setInteger("heat", heat);
        nbtTag.setInteger("energy", energyStorage.getEnergyStored());


        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }



    private CapabilityFluidHandler fluidHandler = new CapabilityFluidHandler();

    // This item handler will hold our three input slots
    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {



        @Override
        protected void onContentsChanged(int slot) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            TileSolderTable.this.markDirty();
        }
    };



    // This item handler will hold our three output slots
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileSolderTable.this.markDirty();
        }
    };




    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    //-------------------------------------------------------------------------------------------------------------

    //reads that saved stuff
    @Override
    public void readFromNBT (NBTTagCompound compound){
        super.readFromNBT(compound);
        if (compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        if (compound.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
        energyStorage.setEnergy(compound.getInteger("energy"));
        heat = compound.getInteger("heat");
        readRestorableFromNBT(compound);


    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setInteger("energy", energyStorage.getEnergyStored());
        compound.setInteger("heat", heat);
        writeRestorableToNBT(compound);
        return compound;
    }

    @Override
    public void readRestorableFromNBT(NBTTagCompound compound) {
        tank.readFromNBT(compound.getCompoundTag("tank"));
    }

    @Override
    public void writeRestorableToNBT(NBTTagCompound compound) {
        NBTTagCompound tankNBT = new NBTTagCompound();
        tank.writeToNBT(tankNBT);
        compound.setTag("tank", tankNBT);
    }



    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        tank.readFromNBT(packet.getNbtCompound().getCompoundTag("tank"));
        clientheat = packet.getNbtCompound().getInteger("heat");
        clientEnergy = packet.getNbtCompound().getInteger("energy");
        energyStorage.setEnergy(packet.getNbtCompound().getInteger("energy"));
        heat = packet.getNbtCompound().getInteger("heat");
    }

    //-------------------------------------------------------------------------------------------------------------

    private MyEnergyStorage energyStorage = new MyEnergyStorage(MAX_POWER, RF_PER_TICK_INPUT);

    //-------------------------------------------------------------------------------------------------------------

    public boolean canInteractWith (EntityPlayer playerIn){
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability (Capability< ? > capability, EnumFacing facing){
        IBlockState state = world.getBlockState(pos);
        /*if(state.getBlock() != ModBlocks.blockSolder || state.getValue(BlockSolderTable.FORMED) == SolderPartIndex.UNFORMED){
            return false;
        }*/
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return  true;
        }
        if (capability == CapabilityEnergy.ENERGY){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T > T getCapability(Capability < T > capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            } else if (facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            } else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
            }
        }
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        if (capability == CapabilityEnergy.ENERGY){
            return  CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        return super.getCapability(capability, facing);
    }


}
