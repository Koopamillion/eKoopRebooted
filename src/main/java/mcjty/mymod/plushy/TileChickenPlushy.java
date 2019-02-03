package mcjty.mymod.plushy;

import mcjty.mymod.furnace.FurnaceState;
import mcjty.mymod.furnace.TileFurnace;
import mcjty.mymod.sound.SoundFurnace;
import mcjty.mymod.tools.MyEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static mcjty.mymod.furnace.FurnaceState.WORKING;

public class TileChickenPlushy extends TileEntity implements ITickable {
    public static final int INPUT_SLOTS = 3;
    public static final int OUTPUT_SLOTS = 9;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    private int randInt = 0;
    Random rand = new Random();
    private int tier1chance = 4;
    private int soundCounter = rand.nextInt(2400);
    private int tier2chance = 2;

    private int tier3chance = 1;





    @Override
    public void update() {
        if (!world.isRemote) {
            randInt = rand.nextInt(19200);

            for(int i = 0; i < tier1chance; i++){
                if(randInt == i){
                    BlockPos pos = getPos();
                    World worldIn = getWorld();
                    worldIn.playSound(null,(double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 1.0f, 1.0F);
                    run(new ItemStack(Items.EGG, 1, 0), Items.EGG);
                    break;
                }
            }
            for(int i = (tier1chance+1); i < (tier2chance+tier1chance+1); i++){
                if(randInt == i){
                    BlockPos pos = getPos();
                    World worldIn = getWorld();
                    worldIn.playSound(null,(double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 1.0f, 1.0F);
                    run(new ItemStack(Items.FEATHER, 1, 0), Items.EGG);
                    break;
                }
            }
            for(int i = (tier2chance+tier1chance+2); i < (tier3chance+tier1chance+tier2chance+2); i++){
                if(randInt == i){
                    BlockPos pos = getPos();
                    World worldIn = getWorld();
                    worldIn.playSound(null,(double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.BLOCKS, 1.0f, 1.0F);
                    run(new ItemStack(Items.CHICKEN, 1, 0), Items.EGG);
                    break;
                }
            }
            if(soundCounter == 0){

                soundCounter = rand.nextInt(2400);
                BlockPos pos = getPos();
                World worldIn = getWorld();

                worldIn.playSound(null,(double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.BLOCKS, 0.7f, 1.0F);
                //    worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundFurnace.furnaceActive, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
            }
            if(soundCounter > 0){
                soundCounter--;
            }

        }






    }


    public void run(ItemStack itemstack, Item item) {

        //    if((ItemHandlerHelper.canItemStacksStack(outputHandler.getStackInSlot(i), itemstack)))

        //    System.out.println("hi");
            ItemHandlerHelper.insertItemStacked(this.outputHandler, itemstack, false);


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
        private ItemStackHandler inputHandler = new ItemStackHandler(0) {

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