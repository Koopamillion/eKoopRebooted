package mcjty.mymod.furnace;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;

public class TileFurnace extends TileEntity implements ITickable {


    public static final int INPUT_SLOTS = 3;
    public static final int OUTPUT_SLOTS = 3;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;




    //start time
    private float furnaceSmeltTimeMax = 100;
    //max end time actually idk anymore
    private float furnaceSmeltTimeMin = 5;
    private float time = furnaceSmeltTimeMax;
    private float progressRemaining = 0;
    private float scale = 0;
    private boolean changeScale = true;


    @Override
    public void update() {
        if (!world.isRemote) {
            if (progressRemaining > 0) {
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
                 System.out.println(time);
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

                    if (changeScale = true){
                        scale = scale + 0.025f;
                    }
                    //set progressremaining to time
                    progressRemaining = time;
                    markDirty();
                    }
                }
                break;
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
        progressRemaining = compound.getInteger("progressRemaining");
    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setFloat("progressRemaining", progressRemaining);
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
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
        return super.getCapability(capability, facing);
    }
}