package mcjty.mymod.soldertable;

import mcjty.mymod.crafting.SolderManager;
import mcjty.mymod.crafting.SolderRecipe;
import mcjty.mymod.furnace.FurnaceState;
import mcjty.mymod.plushy.TileChickenPlushy;
import mcjty.mymod.tools.MyEnergyStorage;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileSolderTable extends TileEntity implements ITickable {

    public static final int INPUT_SLOTS = 9;
    public static final int OUTPUT_SLOTS = 1;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_POWER = 100000;
    public float RF_PER_TICK = 20;
    public static final int RF_PER_TICK_INPUT = 250;
    //static var means it is the same for all acelerating furnaces so only use it for something that wont change per furnace (ie slots)

    private float progressRemaining = 40;

    private int soundCounter = 0;

    private float clientProgress = -1;
    private FurnaceState state = FurnaceState.OFF;
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
                    inputHandler.extractItem(i, 1, false);
                    break;

                }
            }
        }
    }



    private ItemStack getResult(ItemStack stackInSlot){
        SolderRecipe recipe = SolderManager.getRecipe(stackInSlot, stackInSlot, stackInSlot, stackInSlot, stackInSlot);
     //   if(Fluid == Enough){return recipe.getOutput();}
        return  recipe.getOutput();
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

    public void setClientEnergy(int clientEnergy) {
        this.clientEnergy = clientEnergy;
    }

    public int getEnergy(){
        return energyStorage.getEnergyStored();
    }

    @Override
    public NBTTagCompound getUpdateTag () {
        NBTTagCompound nbtTag = super.getUpdateTag();
        return nbtTag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }



    // This item handler will hold our three input slots
    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            ItemStack result = getResult(stack);
            return !result.isEmpty();
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileSolderTable.this.markDirty();
        }
    };


    // This item handler will hold our three output slots
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
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


    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());

        return compound;
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
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
        return super.getCapability(capability, facing);
    }

}
