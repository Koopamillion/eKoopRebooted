package mcjty.mymod.plushy;

import mcjty.mymod.furnace.FurnaceState;
import mcjty.mymod.furnace.TileFurnace;
import mcjty.mymod.tools.MyEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileChickenPlushy extends TileEntity implements ITickable {
    public static final int INPUT_SLOTS = 3;
    public static final int OUTPUT_SLOTS = 3;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_POWER = 100000;
    public float RF_PER_TICK = 20;
    public static final int RF_PER_TICK_INPUT = 250;
    //static var means it is the same for all acelerating furnaces so only use it for something that wont change per furnace (ie slots)


    //start time
    private final float furnaceSmeltTimeMax = 100;
    //max end time actually idk anymore
    private final float furnaceSmeltTimeMin = 5;
    private int count = 0;
    private int bigcount = 0;
    private int biggestcount = 0;

    private int clientEnergy = -1;
    //public int progressInt = 0;

    private boolean changeScale = true;
    //should it accelerate faster and faster? Basically if this is false, its more balanced
    private boolean compound = false;


    @Override
    public void update() {
        if (!world.isRemote) {
            System.out.println(count);
            if(count < 80){
                count++;

            }else{
                bigcount ++;
                count = 0;
                run(new ItemStack(Items.EGG, 1, 0), Items.EGG);
            }
            if(bigcount >= 2){
                bigcount = 0;
                biggestcount ++;
                run(new ItemStack(Items.FEATHER, 1, 0), Items.FEATHER);
            }
            if(biggestcount >= 2){
                biggestcount = 0;
                run(new ItemStack(Items.CHICKEN, 1, 0), Items.CHICKEN);
            }

        }
    }

    public void run(ItemStack itemstack, Item item) {
        for(int i = 0; i < 3; ++i) {
            if((outputHandler.getStackInSlot(i).getCount() >= item.getItemStackLimit(itemstack))&& outputHandler.getStackInSlot(i).getItem() == item)
                continue;
            outputHandler.insertItem(i, itemstack.copy(), false);
            break;
        }
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
            protected void onContentsChanged(int slot) {
                TileChickenPlushy.this.markDirty();
            }
        };


        // This item handler will hold our three output slots
        private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                TileChickenPlushy.this.markDirty();
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
            count = compound.getInteger("count");
            bigcount = compound.getInteger("bigcount");
            biggestcount = compound.getInteger("biggestcount");

        }
        //saves stuff in a file so it can be recalled upon boot up
        @Override
        public NBTTagCompound writeToNBT (NBTTagCompound compound){
            super.writeToNBT(compound);
            compound.setTag("itemsIn", inputHandler.serializeNBT());
            compound.setTag("itemsOut", outputHandler.serializeNBT());
            compound.setInteger("count", count);
            compound.setInteger("bigcount", bigcount);
            compound.setInteger("biggestcount", biggestcount);
            return compound;
        }

        //-------------------------------------------------------------------------------------------------------------


        //-------------------------------------------------------------------------------------------------------------

        public boolean canInteractWith (EntityPlayer playerIn){
            // If we are too far away from this tile entity you cannot use it
            return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
        }

        @Override
        public boolean hasCapability (Capability < ? > capability, EnumFacing facing){
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