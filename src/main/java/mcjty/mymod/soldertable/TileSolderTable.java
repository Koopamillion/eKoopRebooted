package mcjty.mymod.soldertable;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.ModLiquids;
import mcjty.mymod.crafting.SolderManager;
import mcjty.mymod.crafting.SolderRecipe;
import mcjty.mymod.furnace.FurnaceState;
import mcjty.mymod.furnace.TileFurnace;
import mcjty.mymod.plushy.TileChickenPlushy;
import mcjty.mymod.tools.IRestorableTileEntity;
import mcjty.mymod.tools.MyEnergyStorage;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.lwjgl.Sys;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileSolderTable extends TileEntity implements ITickable, IRestorableTileEntity {

    public static final int INPUT_SLOTS = 10;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_POWER = 100000;
    public float RF_PER_TICK = 20;
    public static final int RF_PER_TICK_INPUT = 250;

    //static var means it is the same for all acelerating furnaces so only use it for something that wont change per furnace (ie slots)

    private float progressRemaining = 0;

    private int soundCounter = 0;

    EnumFacing facing = EnumFacing.NORTH;

    private FluidTank tank = new FluidTank(4000);

    private FluidStack clientFluid = tank.getFluid();
    private int clientFluidAmount = tank.getFluidAmount();
    private float clientProgress = -1;

    private int clientEnergy = -1;



    @Override
    public void update() {
        if (!world.isRemote) {

            if (progressRemaining > 0) {

                progressRemaining--;
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


    private void attemptSmelt() {
        for (int i = 0; i < INPUT_SLOTS; i++) {
            ItemStack result = getResult(inputHandler.getStackInSlot(i));
            if (!result.isEmpty()) {
                // This copy is very important!(
                if (insertOutput(result.copy(), false)) {
                    for(int l = 0; l < 9; l++){
                        inputHandler.extractItem(l, 1, false);

                    }
                    tank.drain(1000, true);

                    break;

                }
            }
        }
    }
    private void fluidMake(){
        ItemStack stack = inputHandler.getStackInSlot(9);
        if(stack.getItem() == Items.IRON_INGOT && tank.getFluidAmount() < tank.getCapacity()){

            IFluidHandler handler = getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
            for(int i = 0; i < 100; i++){
                handler.fill(new FluidStack(ModLiquids.solderfluid, 1),true);
            }
            inputHandler.extractItem(9, 1, false);


        }
    }



    private ItemStack getResult(ItemStack stack ){
        SolderRecipe recipe = SolderManager.getRecipe(inputHandler.getStackInSlot(0), inputHandler.getStackInSlot(1), inputHandler.getStackInSlot(2), inputHandler.getStackInSlot(3), inputHandler.getStackInSlot(4), inputHandler.getStackInSlot(5), inputHandler.getStackInSlot(6), inputHandler.getStackInSlot(7), inputHandler.getStackInSlot(8), tank.getFluid());
     //   if(Fluid == Enough){return recipe.getOutput();}
        if (recipe!= null&&tank.getFluidAmount() >= recipe.getInput10Amount()){
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

    public int getClientEnergy() {
        return clientEnergy;
    }
    public int getCapacity(){
        return tank.getCapacity();

    }    public void setClientEnergy(int clientEnergy) {
        this.clientEnergy = clientEnergy;
    }
    public void setClientFluid(FluidStack clientFluid) {
        this.clientFluid = clientFluid;
    }
    public FluidStack getClientFluid() {
       return tank.getFluid();
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
        readRestorableFromNBT(compound);


    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());

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
        return super.getCapability(capability, facing);
    }


}
