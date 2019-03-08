package koopamillion.mymod.generators;

import koopamillion.mymod.furnace.FurnaceState;
import koopamillion.mymod.furnace.TileFurnace;
import koopamillion.mymod.sound.SoundFurnace;
import koopamillion.mymod.tools.MyEnergyStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.lwjgl.Sys;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;


public class TileGenerator extends TileEntity implements ITickable {

    private static final int MAX_POWER = 100000;
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

    private double counter = 0;
    private int beaconPower = -1;
    Random rand = new Random();

    @Override
    public void update() {

        for(int i = 0; i < 255; i++){
            TileEntity below = world.getTileEntity(getPos().down(i));
            if(below instanceof TileEntityBeacon){
                isBeacon = ((TileEntityBeacon) below).getLevels() > 0;
                beaconPower = ((TileEntityBeacon) below).getLevels();
                break;
            }
            isBeacon = false;
        }
        if(isBeacon) {
            if (energyStorage.shouldGenerate(MAX_POWER)) {
                power = power + ((beaconPower + 1) * (beaconPower + 1) * 2 * 2 * 2 * 2) / 1000D;
                if(power >= 1){
                    energyStorage.generatePower(1, MAX_POWER);
                    power --;
                }




             //  spawnParticles();
            }
            spawnParticles();
            double randomValue = rangemin + (rangemax - rangemin) * rand.nextDouble();
            counter = counter + 0.1f;

            if(counter > 360){
                counter = 0;
            }

            counter2 = counter2 + field;
            if(counter2 > 360){
                if(field >= 0.15f){
                    wait = true;
                    field = field - 0.005f;
                }else if(field <= 0.05f){
                    wait = false;
                    field = field + 0.005f;
                }
                if(field > 0.05f && field < 0.15f && wait == true){
                    field = field - 0.005f;
                }else if(field > 0.05f && field < 0.15f && wait == false){
                    field = field + 0.005f;
                }

                counter2 = 0;
            }
        }
        sendEnergy();
    }

    private void spawnParticles(){
        originX = getPos().getX() + 0.5D;
        originY = getPos().getY() + 0.5D;
        originZ = getPos().getZ() + 0.5D;

      //  x = radius*MathHelper.cos(counter) * sin

    //
       x = (radius*Math.sin(counter));
        y = (radius*Math.cos(counter2));
        z = (radius*Math.cos(counter));

      /*  x = radius*Math.cos(counter) * Math.sin(counter);
        y = radius*Math.sin(counter) * Math.sin(counter);
        z = radius * Math.cos(counter);*/

      //  world.spawnParticle(EnumParticleTypes.NOTE, originX +  x , originY + y,originZ + z,0.0D,0.0D,0.0D,50);
        world.spawnParticle(EnumParticleTypes.END_ROD, originX + x , originY + y ,originZ + z,0.0D,0.0D,0.0D,50);

        if(Minecraft.getMinecraft().gameSettings.particleSetting == 0){
        for(int i = 7; i > 0; i--){
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, originX + i/16D , originY - i/16D ,originZ + i/16D,0.0D,0.0D,0.0D,50);
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, originX - i/16D , originY - i/16D ,originZ + i/16D,0.0D,0.0D,0.0D,50);
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, originX + i/16D , originY - i/16D ,originZ - i/16D,0.0D,0.0D,0.0D,50);
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, originX - i/16D , originY - i/16D ,originZ - i/16D,0.0D,0.0D,0.0D,50);
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, originX + i/16D , originY + i/16D ,originZ + i/16D,0.0D,0.0D,0.0D,50);
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, originX + i/16D , originY + i/16D ,originZ - i/16D,0.0D,0.0D,0.0D,50);
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, originX - i/16D , originY + i/16D ,originZ + i/16D,0.0D,0.0D,0.0D,50);
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, originX - i/16D , originY + i/16D ,originZ - i/16D,0.0D,0.0D,0.0D,50);
        }}
       // world.spawnParticle(EnumParticleTypes.REDSTONE, originX + 0.4D , originY + 0.4D ,originZ + 0.4D,0.0D,0.0D,0.0D,50);

    }

    public int getEnergyStored(){
        return energyStorage.getEnergyStored();
    }

    private void sendEnergy() {
        if (energyStorage.getEnergyStored() > 0) {
            for (EnumFacing facing : EnumFacing.VALUES) {
                TileEntity tileEntity = world.getTileEntity(pos.offset(facing));
                if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())) {
                    IEnergyStorage handler = tileEntity.getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
                    if (handler != null && handler.canReceive()) {
                        int accepted = handler.receiveEnergy(energyStorage.getEnergyStored(), false);
                        energyStorage.consumePower(accepted);
                        if (energyStorage.getEnergyStored() <= 0) {
                            break;
                        }
                    }
                }
            }
            markDirty();
        }
    }

    @Override
    public void readFromNBT (NBTTagCompound compound){
        super.readFromNBT(compound);
        energyStorage.setEnergy(compound.getInteger("energy"));


    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("energy", energyStorage.getEnergyStored());
        return compound;
    }

    private MyEnergyStorage energyStorage = new MyEnergyStorage(MAX_POWER, 0);

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY){
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY){
            return  CapabilityEnergy.ENERGY.cast(energyStorage);
        }

        return super.getCapability(capability, facing);
    }
}