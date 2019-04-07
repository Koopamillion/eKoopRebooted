package koopamillion.mymod.plushy;

import koopamillion.mymod.dna.DNAState;
import koopamillion.mymod.dna.TileDNAExtractor;
import koopamillion.mymod.machineitems.ItemMobHolder;
import koopamillion.mymod.machineitems.ItemMobRAM;
import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class TileChickenPlushy extends TileEntity implements ITickable {
    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 9;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    private int randInt = 0;
    Random rand = new Random();
    private int tier1chance = 4;

    private int tier2chance = 2;
    private int tier3chance = 1;
    private boolean hasInitied = false;
    private NBTTagCompound nbt = new NBTTagCompound();
    public String type = "";
    private boolean markUpdate = true;




    private int decrease = 0;




    @Override
    public void update() {
        if (!world.isRemote) {



            if((inputHandler.getStackInSlot(0).getItem() instanceof ItemMobHolder || inputHandler.getStackInSlot(0).getItem() instanceof ItemMobRAM)&& inputHandler.getStackInSlot(0).hasTagCompound()){
                if(!(nbt.getString("type")).equals(inputHandler.getStackInSlot(0).getTagCompound().getString("entity"))){
                    markUpdate = true;
                    String name;
                    try {
                        String entityName = inputHandler.getStackInSlot(0).getTagCompound().getString("entity");
                        nbt.setString("type",  Class.forName(entityName).getCanonicalName());

                        IBlockState state = world.getBlockState(pos);           //marks update to render
                        world.notifyBlockUpdate(pos, state, state, 3);
                    }catch(ClassNotFoundException e){

                        IBlockState state = world.getBlockState(pos);
                        world.notifyBlockUpdate(pos, state, state, 3);

                        nbt.setString("type", "");
                        return;
                    }

                }
            }else{
                nbt.setString("type", "");
                if(markUpdate){             //stops update spamming
                    IBlockState state = world.getBlockState(pos);           //marks update to render... nothing
                    world.notifyBlockUpdate(pos, state, state, 3);
                    markUpdate = false;
                }

            }



            if(nbt.getString("type").isEmpty() || PlushyStuff.getLoot(nbt) == null){        //no loot? dont go to the next stuff and cause lag for no reason
                return;
            }

            if(inputHandler.getStackInSlot(0).getTagCompound().getInteger("dna") <= 118000){
                decrease = inputHandler.getStackInSlot(0).getTagCompound().getInteger("dna");
            }else{
                decrease = 118000;
            }




           randInt = rand.nextInt(120000 - decrease);
       //     randInt = rand.nextInt(200);



            for(int i = 0; i < PlushyStuff.getLoot(nbt).size(); i++){           //gets loot for that mob
                if(randInt < PlushyStuff.getLoot(nbt).size()){
                    run(new ItemStack(PlushyStuff.getLoot(nbt).get(randInt).getItem(), 1, PlushyStuff.getLoot(nbt).get(randInt).getMetadata()));
                    break;
                }
            }







        }






    }


    public void run(ItemStack itemstack) {
            ItemHandlerHelper.insertItemStacked(this.outputHandler, itemstack, false);


    }



    public IBlockState getBlockState(){
        return world.getBlockState(pos);

    }

    public NBTTagCompound getNbt(){
        return nbt;
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

        NBTTagCompound tag = super.getUpdateTag();
        tag.setTag("itemsIn", inputHandler.serializeNBT());
        tag.setTag("itemsOut", outputHandler.serializeNBT());


        tag.setString("mobType", nbt.getString("type"));
        return tag;

    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        if (tag.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) tag.getTag("itemsIn"));
        }
        if (tag.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) tag.getTag("itemsOut"));
        }
        nbt.setString("type", tag.getString("mobType"));






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
                // We need to tell the tile entity that something has changed so
                // that the chest contents is persisted

                markDirty();



            }
        };

        // This item handler will hold our three output slots
        private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
            @Override
            protected void onContentsChanged(int slot) {
                TileChickenPlushy.this.markDirty();
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
        public void readFromNBT (NBTTagCompound compound){
            super.readFromNBT(compound);
            if (compound.hasKey("itemsIn")) {
                inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
            }
            if (compound.hasKey("itemsOut")) {
                outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
            }


            nbt.setString("type", compound.getString("mobType"));

        }
        //saves stuff in a file so it can be recalled upon boot up
        @Override
        public NBTTagCompound writeToNBT (NBTTagCompound compound){
            super.writeToNBT(compound);
            compound.setTag("itemsIn", inputHandler.serializeNBT());
            compound.setTag("itemsOut", outputHandler.serializeNBT());

            compound.setString("mobType", nbt.getString("type"));
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

