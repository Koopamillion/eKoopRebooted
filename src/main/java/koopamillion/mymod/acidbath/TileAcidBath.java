package koopamillion.mymod.acidbath;

import koopamillion.mymod.soldertable.TileSolderTable;
import koopamillion.mymod.tools.IRestorableTileEntity;
import koopamillion.mymod.tools.MyEnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public class TileAcidBath extends TileEntity implements ITickable, IRestorableTileEntity {



    public int RF_PER_USE = 10;
    public static final int MAX_POWER = 10;
    public static final int RF_PER_TICK_INPUT = 10;
    public boolean acidfire = false;
    public boolean redstone = false;
    public boolean electricity = false;
    private FluidTank tank = new FluidTank(1000){
        @Override
        protected void onContentsChanged() {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            markDirty();
        }

    };

    @Override
    public void update() {
        if (!world.isRemote) {
         //   markDirty();

            Block fire = world.getBlockState(getPos().down()).getBlock();
            acidfire = fire == Blocks.FIRE || fire == Blocks.LAVA || fire == Blocks.FLOWING_LAVA || fire == Blocks.MAGMA;
            electricity = fire == Blocks.REDSTONE_BLOCK || energyStorage.getEnergyStored() >= RF_PER_USE;
            redstone = fire == Blocks.REDSTONE_BLOCK;
        }
    }

    public void useEnergy(){
        if(this.energyStorage.getEnergyStored() >= RF_PER_USE){
            this.energyStorage.consumePower(RF_PER_USE);
        }
    }

    public boolean getRedstone(){return redstone;}
    public boolean getAcidFire(){
        return acidfire;
    }
    public boolean getElectricity(){
        return electricity;
    }
    public int getEnergy(){
        return energyStorage.getEnergyStored();
    }


    public FluidTank getTank(){
        return tank;
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

 /*   @Override
    public void handleUpdateTag(NBTTagCompound tag) {
        super.handleUpdateTag(tag);
    }*/


    // This item handler will hold our three output slots

    //-------------------------------------------------------------------------------------------------------------

    //reads that saved stuff


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
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        tank.readFromNBT(pkt.getNbtCompound().getCompoundTag("tank"));
    }

    //-------------------------------------------------------------------------------------------------------------


    @Override
    public void readFromNBT (NBTTagCompound compound){
        super.readFromNBT(compound);
        energyStorage.setEnergy(compound.getInteger("energy"));
        readRestorableFromNBT(compound);


    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        writeRestorableToNBT(compound);
        compound.setInteger("energy", energyStorage.getEnergyStored());
        return compound;
    }

    private MyEnergyStorage energyStorage = new MyEnergyStorage(MAX_POWER, RF_PER_TICK_INPUT);

    //-------------------------------------------------------------------------------------------------------------

    public boolean canInteractWith (EntityPlayer playerIn){
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability (Capability< ? > capability, EnumFacing facing){
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
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(tank);
        }
        if (capability == CapabilityEnergy.ENERGY){
            return  CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        return super.getCapability(capability, facing);
    }
}