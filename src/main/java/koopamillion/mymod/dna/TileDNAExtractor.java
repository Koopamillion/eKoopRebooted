package koopamillion.mymod.dna;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.furnace.FurnaceState;
import koopamillion.mymod.furnace.TileFurnace;
import koopamillion.mymod.machineitems.ItemMobHolder;
import koopamillion.mymod.machineitems.ItemMobRAM;
import koopamillion.mymod.machineitems.ItemSolderIngot;
import koopamillion.mymod.saturator.FoodSenser;
import koopamillion.mymod.tools.Helper;
import koopamillion.mymod.tools.MyEnergyStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.lwjgl.Sys;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class TileDNAExtractor extends TileEntity  implements ITickable {

    public static final int MAX_POWER = 100000;
    public static final int RF_PER_TICK_INPUT = 2000;
    public static final int RF_PER_TICK = 50;
    public static final int RF_IDLE = 2;

    private NBTTagCompound nbt = new NBTTagCompound();
    private int dnaCount = 0;
  //  private int dnaCount = 256;

    private double px = 0;
    private double py = 0;
    private double pz = 0;

    public static final int SIZE = 3;
    public static final int INPUT_SLOTS = 2;
    public static final int OUTPUT_SLOTS = 1;
    public boolean debug = false;


    private double tlx = -5;
    private double tly = -3;
    private double tlz = -5;
    private double trx = 5;
    private double tryy = 3;
    private double trz = 5;
    private float tick = 0;

    private int dnaFromStack = 0;
    private int cooldown = 20;

    private int clientEnergy = -1;
    private float progressRemaining = 0;
    private int clientDNA = -1;
    private float clientProgress = -1;

    private DNAState state = DNAState.OFF;

    private NBTTagCompound clienttag = new NBTTagCompound();


    public int newDna = 0;
    public int dnaToTakeaway = 0;
    public boolean go = false;
    private int trackCounter = 0;
    private AxisAlignedBB trackingBox;

    @Override
    public void update() {


        if(world.isRemote) {
            if (debug) {
                Helper.spawnParticleRange(tlx, tly, tlz, trx, tryy, trz, pos, EnumParticleTypes.END_ROD, world);
            }
            if(go){
                Helper.spawnParticleLaserToPlayer(pos.getX(), pos.getY(), pos.getZ(), px,py,pz, world, EnumParticleTypes.PORTAL);
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
            if(!inputHandler.getStackInSlot(1).isEmpty()){
                if(inputHandler.getStackInSlot(1).getItem() instanceof ItemMobRAM||inputHandler.getStackInSlot(1).getItem() instanceof ItemMobHolder){
                    if(inputHandler.getStackInSlot(1).hasTagCompound()){
                        if(inputHandler.getStackInSlot(1).getTagCompound().getInteger("dna") > 0 && (nbt.getString("type").isEmpty() || nbt.getString("type").equals(inputHandler.getStackInSlot(1).getTagCompound().getString("entity")))){
                            nbt.setString("type", inputHandler.getStackInSlot(1).getTagCompound().getString("entity"));
                            dnaCount = dnaCount + inputHandler.getStackInSlot(1).getTagCompound().getInteger("dna");
                            inputHandler.getStackInSlot(1).getTagCompound().setString("entity","");
                            inputHandler.getStackInSlot(1).getTagCompound().setInteger("dna",0);
                        }
                    }
                }
            }
            if(shouldRun()) {
                if (progressRemaining > 0) {
                    setState(DNAState.WORKING);
                    progressRemaining--;
                    energyStorage.consumePower(RF_PER_TICK);
                    if (progressRemaining <= 0) {
                        attemptSmelt();
                    }
                    markDirty();
                } else {
                    startSmelt();
                }

            }else{
                setState(DNAState.OFF);
            }
        }
    }

    private boolean shouldRun(){
        if(inputHandler.getStackInSlot(0).getItem() instanceof ItemMobHolder || inputHandler.getStackInSlot(0).getItem() instanceof ItemMobRAM){
            if(outputHandler.getStackInSlot(0).isEmpty()){
                if(dnaCount > 0 && energyStorage.getEnergyStored() >= RF_PER_TICK){
                    if(inputHandler.getStackInSlot(0).hasTagCompound() && inputHandler.getStackInSlot(0).getItem() instanceof ItemMobRAM){
                        if(inputHandler.getStackInSlot(0).getTagCompound().getInteger("dna") >= ItemMobRAM.maxDNA && inputHandler.getStackInSlot(0).getTagCompound().getString("entity").equals(nbt.getString("type"))){
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public void setState(DNAState state) {
        if (this.state != state) {
            this.state = state;
            markDirty();
            IBlockState blockState = world.getBlockState(pos);
            getWorld().notifyBlockUpdate(pos, blockState, blockState, 3);
        }
    }

    private void startSmelt() {

            ItemStack result = inputHandler.getStackInSlot(0);
            if(result.getItem() instanceof ItemMobHolder || result.getItem() instanceof ItemMobRAM) {

                if (insertOutput(result.copy(), true)) {
                    progressRemaining = 40;
                    markDirty();
                }


            }



    }
    private void attemptSmelt() {
            ItemStack result = inputHandler.getStackInSlot(0);
            if (result.getItem() instanceof ItemMobHolder) {
                // This copy is very important!(
                if (insertOutput(result.copy(), false)) {

                        inputHandler.extractItem(0, 1, false);
                        ItemStack stack = outputHandler.getStackInSlot(0);
                        if(stack.hasTagCompound()){
                            if(!stack.getTagCompound().getString("entity").equals(nbt.getString("type"))){
                                stack.getTagCompound().setString("entity", nbt.getString("type"));
                                stack.getTagCompound().setInteger("dna", dnaCount);
                            }else{
                                if(stack.getTagCompound().getInteger("dna") > 0){
                                    dnaFromStack = stack.getTagCompound().getInteger("dna");
                                    System.out.println(dnaFromStack);
                                    stack.getTagCompound().setInteger("dna", dnaCount + dnaFromStack);
                                }else{
                                    stack.getTagCompound().setInteger("dna", dnaCount);
                                }
                            }


                        }else{
                            stack.setTagCompound(new NBTTagCompound());
                            stack.getTagCompound().setString("entity", nbt.getString("type"));
                            stack.getTagCompound().setInteger("dna", dnaCount);

                        }
                        dnaCount = 0;
                        nbt.setString("type", "");
                }
            }else if (result.getItem() instanceof ItemMobRAM) {
                // This copy is very important!(
                if (insertOutput(result.copy(), false)) {

                    inputHandler.extractItem(0, 1, false);
                    ItemStack stack = outputHandler.getStackInSlot(0);
                    if(stack.hasTagCompound()){
                        if(!stack.getTagCompound().getString("entity").equals(nbt.getString("type"))){
                            stack.getTagCompound().setString("entity", nbt.getString("type"));
                            if(dnaCount <= ItemMobRAM.maxDNA){
                                stack.getTagCompound().setInteger("dna", dnaCount);
                                dnaToTakeaway = dnaCount;
                            }else{
                                stack.getTagCompound().setInteger("dna", 256);
                                dnaToTakeaway = 256;
                            }

                        }else{
                            if(stack.getTagCompound().getInteger("dna") > 0){
                                dnaFromStack = stack.getTagCompound().getInteger("dna");
                                System.out.println(dnaFromStack);
                                if(dnaFromStack < ItemMobRAM.maxDNA){
                                    newDna = ItemMobRAM.maxDNA - dnaFromStack;
                                    if(dnaCount > newDna){
                                        stack.getTagCompound().setInteger("dna", newDna + dnaFromStack);
                                        dnaToTakeaway = newDna;
                                    }else{
                                        stack.getTagCompound().setInteger("dna", dnaCount + dnaFromStack);
                                        dnaToTakeaway = dnaCount;
                                    }
                                }

                            }else{
                                if(dnaCount <= ItemMobRAM.maxDNA){
                                    stack.getTagCompound().setInteger("dna", dnaCount);
                                    dnaToTakeaway = dnaCount;
                                }else{
                                    dnaToTakeaway = 256;
                                    stack.getTagCompound().setInteger("dna", 256);
                                }
                            }
                        }


                    }else{
                        stack.setTagCompound(new NBTTagCompound());
                        stack.getTagCompound().setString("entity", nbt.getString("type"));
                        if(dnaCount <= ItemMobRAM.maxDNA){
                            stack.getTagCompound().setInteger("dna", dnaCount);
                            dnaToTakeaway = dnaCount;
                        }else{
                            dnaToTakeaway = 256;
                            stack.getTagCompound().setInteger("dna", 256);
                        }

                    }
                    dnaCount = dnaCount - dnaToTakeaway;
                    if(!(dnaCount > 0)){
                        nbt.setString("type", "");
                    }
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

    @Override
    public NBTTagCompound getUpdateTag () {

        NBTTagCompound tag = super.getUpdateTag();
        tag.setTag("itemsIn", inputHandler.serializeNBT());
        tag.setTag("itemsOut", outputHandler.serializeNBT());
        tag.setInteger("energy", energyStorage.getEnergyStored());
        tag.setBoolean("debug", debug);
        tag.setString("mobType", nbt.getString("type"));
        tag.setInteger("dnacount", dnaCount);
        tag.setBoolean("go", go);

        tag.setDouble("xx", px);
        tag.setDouble("yy", py);
        tag.setDouble("zz", pz);
        tag.setInteger("state", state.ordinal());
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
        clientEnergy = tag.getInteger("energy");
        energyStorage.setEnergy(tag.getInteger("energy"));
        debug = tag.getBoolean("debug");
        nbt.setString("type", tag.getString("mobType"));
        dnaCount = tag.getInteger("dnacount");
        go = tag.getBoolean("go");

        px = tag.getDouble("xx");
        py = tag.getDouble("yy");
        pz = tag.getDouble("zz");

        int stateIndex = tag.getInteger("state");
        if (world.isRemote && stateIndex != state.ordinal()) {
            state = DNAState.VALUES[stateIndex];
            world.markBlockRangeForRenderUpdate(pos, pos);
        }

    }


    public NBTTagCompound getClienttag() {
        return clienttag;
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

    public int getDnaCount(){return dnaCount;}

    public float getClientDNA() {
        return clientDNA;
    }

    public void setClientDNA(int clientDNA) {
        this.clientDNA = clientDNA;
    }


    public void setClienttag(NBTTagCompound clienttag) {
        this.clienttag = clienttag;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket () {
        return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
    }

    public ItemStackHandler getInputHandler(){
        return inputHandler;
    }

    private ItemStackHandler inputHandler = new ItemStackHandler(INPUT_SLOTS) {

   /*     @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(stack);
            return !result.isEmpty();
        }*/


        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileDNAExtractor.this.markDirty();
        }
    };


    // This item handler will hold our three output slots
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {
        @Override
        protected void onContentsChanged(int slot) {
            // We need to tell the tile entity that something has changed so
            // that the chest contents is persisted
            TileDNAExtractor.this.markDirty();
        }
        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }
    };

    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputHandler, outputHandler);

    public DNAState getState() {
        return state;
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
        if (compound.hasKey("itemsOut")) {
            outputHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsOut"));
        }
        energyStorage.setEnergy(compound.getInteger("energy"));
        debug = compound.getBoolean("debug");
        nbt.setString("type", compound.getString("mobType"));
        dnaCount = compound.getInteger("dnacount");



    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());
        compound.setInteger("energy", energyStorage.getEnergyStored());
        compound.setBoolean("debug", debug);
        compound.setString("mobType", nbt.getString("type"));
        compound.setInteger("dnacount", dnaCount);
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






    public void extractDna(EntityLivingBase entity) {




        if (getTrackingBox().contains(entity.getPositionVector())) {
            if(!(entity instanceof EntityPlayer) && energyStorage.getEnergyStored() >= RF_PER_TICK && cooldown <= 0) {

                px = entity.getPositionVector().x;
                py = entity.getPositionVector().y;
                pz = entity.getPositionVector().z;
                if(nbt.getString("type").isEmpty() && dnaCount >= 0){
                    go = true;
                    entity.writeToNBT(nbt);
                    //USE ENTITYLIST.getKey(Entity).toString ?????????
                    nbt.setString("type", entity.getClass().getCanonicalName());
                    entity.setDropItemsWhenDead(false);
                    entity.setHealth(0);
                    dnaCount++;
                    energyStorage.consumePower(RF_PER_TICK);
                    IBlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos, state, state, 3);
                    cooldown = 20;
                    return;
                }
                if(!nbt.getString("type").isEmpty()){
                    if(nbt.getString("type").equals(entity.getClass().getCanonicalName())){
                        go = true;
                        entity.setDropItemsWhenDead(false);
                        entity.setHealth(0);
                        energyStorage.consumePower(RF_PER_TICK);
                        dnaCount++;
                        IBlockState state = world.getBlockState(pos);
                        world.notifyBlockUpdate(pos, state, state, 3);
                        cooldown = 20;

                        return;
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
            if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            } else if (facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputHandler);
            } else {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(outputHandler);
            }
        }
        if (capability == CapabilityEnergy.ENERGY){
            return  CapabilityEnergy.ENERGY.cast(energyStorage);
        }


        return super.getCapability(capability, facing);
    }
}