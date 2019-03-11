package koopamillion.mymod.saturator;

import koopamillion.mymod.tools.Helper;
import koopamillion.mymod.tools.MyEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;

public class TileSaturator extends TileEntity implements ITickable {

    public static final int MAX_POWER = 25000;
    public static final int RF_PER_TICK_INPUT = 100;
    public static final int RF_PER_TICK = 200;
    public static final int RF_IDLE = 2;
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

    private  boolean needfood = false;

    private double px = 0;
    private double py = 0;
    private double pz = 0;

    private float field = 0.1f;
    private boolean wait = false;
    public static final int SIZE = 9;
    public static final int INPUT_SLOTS = 9;
    public boolean debug = false;
    public boolean go = false;

    private double tlx = -5;
    private double tly = -3;
    private double tlz = -5;
    private double trx = 5;
    private double tryy = 3;
    private double trz = 5;
    private float tick = 0;

    private int cooldown = 20;


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


        if(world.isRemote) {
            if (debug) {
                Helper.spawnParticleRange(tlx, tly, tlz, trx, tryy, trz, pos, EnumParticleTypes.END_ROD, world);
            }
            if(go){
                Helper.spawnParticleLaserToPlayer(pos.getX(), pos.getY(), pos.getZ(), px,py,pz, world, EnumParticleTypes.VILLAGER_HAPPY);
               go= false;

            }



        }


        if (!world.isRemote) {
            go = false;


            if (energyStorage.getEnergyStored() >= RF_IDLE) {
                energyStorage.consumePower(RF_IDLE);
                trackCounter--;
                if (trackCounter <= 0) {
                    trackCounter = 20;
                    findEntities();
                }
                //stops a very weird glitch
                cooldown--;
                if (cooldown <= 0) {
                    cooldown = 0;
                }


            }
        }
    }

    @Override
    public NBTTagCompound getUpdateTag () {

        NBTTagCompound tag = super.getUpdateTag();
        tag.setTag("itemsIn", inputHandler.serializeNBT());
        tag.setInteger("energy", energyStorage.getEnergyStored());
        tag.setBoolean("debug", debug);
        tag.setBoolean("go", go);

        tag.setDouble("xx", px);
        tag.setDouble("yy", py);
        tag.setDouble("zz", pz);
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
        debug = tag.getBoolean("debug");
        go = tag.getBoolean("go");
        px = tag.getDouble("xx");
        py = tag.getDouble("yy");
        pz = tag.getDouble("zz");

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



    /*    @Override
        protected void onContentsChanged(int slot) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            TileSaturator.this.markDirty();
        }*/
    };


    public boolean runnable(EntityPlayer entity, ItemStack stack){

        if(!stack.isEmpty()) {
            if(stack.getItem() instanceof ItemFood) {
                return entity.getFoodStats().needFood() || ((ItemFood) stack.getItem()).alwaysEdible;
            }
        }
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
        debug = compound.getBoolean("debug");




    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setInteger("energy", energyStorage.getEnergyStored());
        compound.setBoolean("debug", debug);
        return compound;
    }

    private void findEntities() {
        FoodSenser.instance.clear(world.provider.getDimension(), pos);

        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, getTrackingBox());
        for (EntityLivingBase entity : entities) {
            FoodSenser.instance.register(world.provider.getDimension(), pos, entity.getUniqueID());
            FoodSenser.instance.eatfood(world, entity);
        }
    }



    public void setDebug(boolean b){
        this.debug = b;
    }

    public boolean getDebug(){
        return debug;
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

    public void setTick(float f){
        tick = f;
    }





    @Override
    public void invalidate() {
        super.invalidate();
        FoodSenser.instance.remove(world.provider.getDimension(), pos);
    }


    //add raw chicken effects etc, traacking box TESR and particles


    public AxisAlignedBB getTrackingBox() {
        if (trackingBox == null) {
            trackingBox = new AxisAlignedBB(pos.add(tlx, tly, tlz), pos.add(trx, tryy, trz));
        }
        return trackingBox;
    }






    public void feedPlayer(EntityLivingBase entity) {


        if (getTrackingBox().contains(entity.getPositionVector())) {
            if(entity instanceof EntityPlayer && energyStorage.getEnergyStored() >= RF_PER_TICK && cooldown <= 0) {



                    //   px = entity.getPosition().getX();
                    //    py = entity.getPosition().getY();
                    //  pz = entity.getPosition().getZ();

                    px = entity.getPositionVector().x;
                    py = entity.getPositionVector().y;
                    pz = entity.getPositionVector().z;

                    for (int i = 0; i < INPUT_SLOTS; i++) {
                        ItemStack stack = inputHandler.getStackInSlot(i);
                        if (runnable((EntityPlayer) entity, stack) && !stack.isEmpty()) {
                            if (inputHandler.getStackInSlot(i).getItem() instanceof ItemFood) {
                                stack.getItem().onItemUseFinish(stack, world, entity);
                                energyStorage.consumePower(RF_PER_TICK);
                                //nutrition support
                                MinecraftForge.EVENT_BUS.post(new LivingEntityUseItemEvent.Finish(entity, stack, 0, ItemStack.EMPTY));
                                go = true;
                                IBlockState state = world.getBlockState(pos);
                                world.notifyBlockUpdate(pos, state, state, 3);
                                cooldown = 20;

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