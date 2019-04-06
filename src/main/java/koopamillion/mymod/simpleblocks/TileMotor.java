package koopamillion.mymod.simpleblocks;

import koopamillion.mymod.tools.IRestorableTileEntity;
import koopamillion.mymod.tools.MyEnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;

public class TileMotor  extends TileEntity implements ITickable{




    public static final int MAX_POWER = 1000;
    public static final int RF_PER_TICK_INPUT = 25;
    public static final int RF_PER_TICK = 5;

    public int RPMproduced = 0;
    public static final int maxRPM = 4200;

    @Override
    public void update() {
        if (!world.isRemote) {
           makeRPM();
        }
    }



    public int getEnergy(){
        return energyStorage.getEnergyStored();
    }


    public void makeRPM(){
        if(energyStorage.getEnergyStored() >= RF_PER_TICK ){
            if(RPMproduced < maxRPM){
                RPMproduced++;
                energyStorage.consumePower(RF_PER_TICK);
            }
        }else{
            if(RPMproduced > 0){
                RPMproduced--;
            }
        }
        if(RPMproduced > maxRPM){
            RPMproduced = maxRPM;
        }
        if(RPMproduced <0){
            RPMproduced = 0;
        }
    }




    //-------------------------------------------------------------------------------------------------------------


    public int getRPMproduced() {
        return RPMproduced;
    }

    @Override
    public void readFromNBT (NBTTagCompound compound){
        super.readFromNBT(compound);
        energyStorage.setEnergy(compound.getInteger("energy"));
        RPMproduced = compound.getInteger("rpm");

    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("energy", energyStorage.getEnergyStored());
        compound.setInteger("rpm", RPMproduced);
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
        if (capability == CapabilityEnergy.ENERGY){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T > T getCapability(Capability < T > capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY){
            return  CapabilityEnergy.ENERGY.cast(energyStorage);
        }
        return super.getCapability(capability, facing);
    }
}