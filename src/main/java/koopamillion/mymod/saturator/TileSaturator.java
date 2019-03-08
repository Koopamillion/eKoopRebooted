package koopamillion.mymod.saturator;

import koopamillion.mymod.soldertable.TileSolderTable;
import koopamillion.mymod.tools.MyEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.lwjgl.Sys;

import javax.annotation.Nullable;
import java.util.*;

public class TileSaturator extends TileEntity implements ITickable {

    public static final int MAX_POWER = 25000;
    public static final int RF_PER_TICK_INPUT = 100;
    private boolean isBeacon = false;
    private double power = 0;
    private double radius = 1.2;
    private double originX = 0;
    private double originY = 0;
    private double originZ = 0;
    private double x = 0;
    private double y = 0;
    private double z = 0;
    private double t = 0;
    private double rangemin = 0.0d;
    private double rangemax = 1.0d;
    private double counter2 = 360;
    private float field = 0.1f;
    private boolean wait = false;
    public static final int SIZE = 9;
    public static final int INPUT_SLOTS = 9;

    private double counter = 0;
    private int beaconPower = -1;
    Random rand = new Random();
    private boolean createSaturation = true;
    private int maxsat = 20;
    private int clientEnergy = -1;



    private int trackCounter = 0;
    private AxisAlignedBB trackingBox;

    @Override
    public void update() {
        if (!world.isRemote) {
            trackCounter--;
            if (trackCounter <= 0) {
                trackCounter = 20;
                findEntities();

            }


        }
    }

    @Override
    public NBTTagCompound getUpdateTag () {

        NBTTagCompound tag = super.getUpdateTag();
        tag.setTag("itemsIn", inputHandler.serializeNBT());
        tag.setInteger("energy", energyStorage.getEnergyStored());
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
        clientEnergy = tag.getInteger("energy");
        energyStorage.setEnergy(tag.getInteger("energy"));
    }


    //make it so it dosent say -1 energy on the bar

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    public ItemStackHandler getInputHandler(){
        return inputHandler;
    }

    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {



        @Override
        protected void onContentsChanged(int slot) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            TileSaturator.this.markDirty();
        }
    };


    public boolean runnable(EntityPlayer entity){
        return entity.getFoodStats().needFood();

    }



    public int getEnergyStored(){
        return energyStorage.getEnergyStored();
    }

    private MyEnergyStorage energyStorage = new MyEnergyStorage(MAX_POWER, RF_PER_TICK_INPUT);

    @Override
    public void readFromNBT (NBTTagCompound compound){
        super.readFromNBT(compound);
        if (compound.hasKey("itemsIn")) {
            inputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsIn"));
        }
        energyStorage.setEnergy(compound.getInteger("energy"));


    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setInteger("energy", energyStorage.getEnergyStored());
        return compound;
    }

    private void findEntities() {
        DamageTracker.instance.clear(world.provider.getDimension(), pos);

        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, getTrackingBox());
        for (EntityLivingBase entity : entities) {
            DamageTracker.instance.register(world.provider.getDimension(), pos, entity.getUniqueID());
            DamageTracker.instance.eatfood(world, entity);
        }
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
    public void invalidate() {
        super.invalidate();
        DamageTracker.instance.remove(world.provider.getDimension(), pos);
    }


    //add raw chicken effects etc, traacking box TESR and particles


    private AxisAlignedBB getTrackingBox() {
        if (trackingBox == null) {
            trackingBox = new AxisAlignedBB(pos.add(-5, -3, -5), pos.add(5, 3, 5));
        }
        return trackingBox;
    }

    public void senseDamage(EntityLivingBase entity) {

        if (getTrackingBox().contains(entity.getPositionVector())) {
            if(entity instanceof EntityPlayer){
                if(runnable((EntityPlayer)entity)){
                    for(int i = 0; i < INPUT_SLOTS; i++){
                        if(inputHandler.getStackInSlot(i).getItem() instanceof ItemFood && runnable((EntityPlayer)entity)) {
                            ((EntityPlayer) entity).getFoodStats().addStats(((ItemFood) inputHandler.getStackInSlot(i).getItem()), inputHandler.getStackInSlot(i));
                            inputHandler.extractItem(i,1,false);
                            break;
                        }
                    }

                }

            }
        }
    }

    public boolean canInteractWith (EntityPlayer playerIn){
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
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
        }
        if (capability == CapabilityEnergy.ENERGY){
            return  CapabilityEnergy.ENERGY.cast(energyStorage);
        }


        return super.getCapability(capability, facing);
    }
}