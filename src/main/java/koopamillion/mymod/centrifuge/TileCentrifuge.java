package koopamillion.mymod.centrifuge;

import koopamillion.mymod.crafting.CentrifugeManager;
import koopamillion.mymod.crafting.CentrifugeRecipe;
import koopamillion.mymod.crafting.SolderManager;
import koopamillion.mymod.network.Messages;
import koopamillion.mymod.network.VanillaPackets;
import koopamillion.mymod.simpleblocks.BlockMotor;
import koopamillion.mymod.simpleblocks.TileMotor;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import scala.reflect.internal.Trees;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class TileCentrifuge extends TileEntity implements ITickable {


    public static final int INPUT_SLOTS = 1;
    public static final int OUTPUT_SLOTS = 9;
    public static final int SIZE = INPUT_SLOTS + OUTPUT_SLOTS;
    public static final int MAX_RPM = 10000;

    private int tick = 0;


    private float progressRemaining = 0;
    private float clientProgress = -1;




    EnumFacing facing = EnumFacing.NORTH;


    public double cosmeticRPM = 0;
    public int idleRPM = 0;
    //rpm provided by motor
    public int clientIdleRPM = 0;
    public int clientRPM = 0;
    public int RPM = 0;
    public int oldRPM = 0;

    public int craftRPM = 0;

    public int clientCraftRPM = 0;

    public int rot = 0;
    public int lastrot = 0;

    public int clientGUIRPM = -1;

    public int clientRot = 0;
    public int lastClientRot = 0;
    public int recievedRPM = 0;
    public int clientRecievedRPM = 0;

    public int finaltickcounter = 0;

    public boolean isRunning = false;

    public boolean clientisRunning = false;

    public List<ItemStack> outputed = new ArrayList<>();






    public double dealwithpartialticks(double partialticks){
        cosmeticRPM = cosmeticRPM +  MathHelper.clampedLerp(lastClientRot, clientRot, partialticks) / 100;




        return cosmeticRPM;
    }

    /**
     * MY VARIABLES HAVE BAD NAMES
     *
     * ROT IS NOW AUTO SYNCED TO CLIENT SO WE CAN CONTROL CENTRIFUGE SPIN FROM SERVER
     */


    @Override
    public void update() {
        if(world.isRemote){


            }//   cosmeticRPM = cosmeticRPM + amounttoclientincrease;



        if (!world.isRemote) {

            if(!inputHandler.getStackInSlot(0).isEmpty()){
                if(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0))!=null){
                    craftRPM = CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getRPM();
                }
            }else{
                craftRPM = 0;
            }

            if(shouldruntwo()){
                isRunning = true;

                if(rot >= craftRPM){
                    if (progressRemaining > 0) {

                        progressRemaining--;
                        if (progressRemaining <= 0) {
                            attemptSmelt();
                        }
                        markDirty();
                    } else {
                        startSmelt();
                    }
                }
            }else{
                isRunning = false;
              //  craftRPM = 0;
            }
            controlRPM();


            VanillaPackets.dispatchTEToNearbyPlayers(this);








                finaltickcounter++;

                //set this and is running when it crafts









                // System.out.println(tanker);




            oldRPM = RPM;
        }
    }

/*    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }*/



    public void controlRPM(){

        if(world.getTileEntity(getPos().down()) instanceof  TileMotor && !world.isRemote){
            if(((TileMotor) world.getTileEntity(getPos().down())).getRPMproduced() <= MAX_RPM){
                idleRPM = ((TileMotor) world.getTileEntity(getPos().down())).getRPMproduced();
            }else{
                idleRPM = MAX_RPM;
            }

        }else{idleRPM = 0;}
        if(rot < 0)
            rot = 0;

        if(isRunning) {

            if(rot > MAX_RPM){
                rot = MAX_RPM;
            }

            lastrot = rot;

            if(rot > idleRPM){
                rot = idleRPM;
            }

            if(rot < craftRPM){
                rot+=4;
            }
            if(rot > craftRPM){
                rot -=1;
            }



        }else{

            lastrot = rot;
            if(rot > 0){
                rot -=8;
            }

        }



    }

    public int getRotation(){
        return clientRPM;
    }

    public int getRPM(){
        return RPM;
    }

    public float getProgressRemaining() {
        return progressRemaining;
    }

    public float getClientProgress() {
        return clientProgress;
    }

    public void setClientIdleRPM(int clientIdleRPM) {
        this.clientIdleRPM = clientIdleRPM;
    }

    public void setIdleRPM(int idleRPM) {
        this.idleRPM = idleRPM;
    }

    public int getCraftRPM() {
        return craftRPM;
    }

    public int getClientCraftRPM() {
        return clientCraftRPM;
    }

    public void setClientCraftRPM(int clientCraftRPM) {
        this.clientCraftRPM = clientCraftRPM;
    }

    public int getIdleRPM() {
        return idleRPM;
    }

    public int getClientIdleRPM() {
        return clientIdleRPM;
    }

    public void setClientProgress(float clientProgress) {
        this.clientProgress = clientProgress;
    }

    public EnumFacing getFacing(){
        return this.facing;
    }

    public int getRecievedRPM() {
        return recievedRPM;
    }

    public void setClientGUIRPM(int clientGUIRPM) {
        this.clientGUIRPM = clientGUIRPM;
    }

    public int getClientGUIRPM() {
        return clientGUIRPM;
    }

    public int getRot() {
        return rot;
    }

    public void setRecievedRPM(int recievedRPM) {
        this.recievedRPM = recievedRPM;
    }

    public void setClientRecievedRPM(int clientRecievedRPM) {
        this.clientRecievedRPM = clientRecievedRPM;
    }

    public int getClientRecievedRPM() {
        return clientRecievedRPM;
    }

    public IBlockState getBlockState(){
        return world.getBlockState(pos);

    }

    public void setClientRPM(int clientRPM) {
        this.clientRPM = clientRPM;
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

    Random rand = new Random();




     private void startSmelt() {
         if (shouldruntwo()) {
             progressRemaining = 40;
             markDirty();
         }
     }

     //FIX CRAFTING BUG



    private void attemptSmelt() {
        if (shouldruntwo()) {
            if(getResultTwo() == null){
               return;
            }

            for(ItemStack stack: getResultTwo()){
                insertOutput(stack.copy(), false);
            }

            List<ItemStack> l = Arrays.asList(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getInput1().getMatchingStacks());

            inputHandler.extractItem(0, l.get(0).getCount(), false);


        }
    }








    public boolean shouldruntwo(){
        List<ItemStack> stacks = getResultTwo();
        List<ItemStack> stacker = new ArrayList<>();
        if(inputHandler.getStackInSlot(0).isEmpty()) return false;

        if(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)) == null) return false;
        if(!CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput1().isEmpty())
        stacker.add(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput1());
        if(!CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput2().isEmpty())
        stacker.add(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput2());
        if(!CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput3().isEmpty())
        stacker.add(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput3());
        if(!CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput4().isEmpty())
        stacker.add(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput4());
        if(!CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput5().isEmpty())
        stacker.add(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput5());
        if(!CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput6().isEmpty())
        stacker.add(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput6());
        if(!CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput7().isEmpty())
        stacker.add(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput7());
        if(!CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput8().isEmpty())
        stacker.add(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput8());
        if(!CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput9().isEmpty())
        stacker.add(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput9());
        if(stacks == null) return false;
        for (ItemStack stack: stacker) {
            if(insertOutput(stack, true)){

            }else{
                return false;
            }
        }

        if(idleRPM <= CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getRPM()){
            return false;
        }

        List<ItemStack> ll = Arrays.asList(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getInput1().getMatchingStacks());

        if(!(inputHandler.getStackInSlot(0).getCount() >=(ll.get(0).getCount()))) return false;

        if(CentrifugeManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getRPM() > MAX_RPM) return false;
        return true;
    }
    
    public List<ItemStack> getResultTwo(){
        ItemStack stack = inputHandler.getStackInSlot(0);
        CentrifugeRecipe recipe = CentrifugeManager.getRecipeForStatsOnly(stack);
        List<ItemStack> finallist = new ArrayList<>();
        if(recipe == null) return null;

        if(!recipe.getOutput1().isEmpty()){
            if(recipe.getChance1() == 0){
                finallist.add(recipe.getOutput1());
            }else{
                if(rand.nextInt(100) <= recipe.getChance1()){
                    finallist.add(recipe.getOutput1());
                }
            }
        }
        if(!recipe.getOutput2().isEmpty()){
            if(recipe.getChance2() == 0){
                finallist.add(recipe.getOutput2());
            }else{
                if(rand.nextInt(100) <= recipe.getChance2()){
                    finallist.add(recipe.getOutput2());
                }
            }
        }
        if(!recipe.getOutput3().isEmpty()){
            if(recipe.getChance3() == 0){
                finallist.add(recipe.getOutput3());
            }else{
                if(rand.nextInt(100) <= recipe.getChance3()){
                    finallist.add(recipe.getOutput3());
                }
            }
        }
        if(!recipe.getOutput4().isEmpty()){
            if(recipe.getChance4() == 0){
                finallist.add(recipe.getOutput4());
            }else{
                if(rand.nextInt(100) <= recipe.getChance4()){
                    finallist.add(recipe.getOutput4());
                }
            }
        }

        if(!recipe.getOutput5().isEmpty()){
            if(recipe.getChance5() == 0){
                finallist.add(recipe.getOutput5());
            }else{
                if(rand.nextInt(100) <= recipe.getChance5()){
                    finallist.add(recipe.getOutput5());
                }
            }
        }
        if(!recipe.getOutput6().isEmpty()){
            if(recipe.getChance6() == 0){
                finallist.add(recipe.getOutput6());
            }else{
                if(rand.nextInt(100) <= recipe.getChance6()){
                    finallist.add(recipe.getOutput6());
                }
            }
        }
        if(!recipe.getOutput7().isEmpty()){
            if(recipe.getChance7() == 0){
                finallist.add(recipe.getOutput7());
            }else{
                if(rand.nextInt(100) <= recipe.getChance7()){
                    finallist.add(recipe.getOutput7());
                }
            }
        }
        if(!recipe.getOutput8().isEmpty()){
            if(recipe.getChance8() == 0){
                finallist.add(recipe.getOutput8());
            }else{
                if(rand.nextInt(100) <= recipe.getChance8()){
                    finallist.add(recipe.getOutput8());
                }
            }
        }

        if(!recipe.getOutput9().isEmpty()){
            if(recipe.getChance9() == 0){
                finallist.add(recipe.getOutput9());
            }else{
                if(rand.nextInt(100) <= recipe.getChance9()){
                    finallist.add(recipe.getOutput9());
                }
            }
        }
        return finallist;
    }







    @Override
    public NBTTagCompound getUpdateTag () {
        NBTTagCompound nbtTag = super.getUpdateTag();
        nbtTag.setInteger("clientRot", rot);
        nbtTag.setInteger("lastClientRot", lastrot);
        nbtTag.setBoolean("isrunning", isRunning);


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
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            TileCentrifuge.this.markDirty();
        }
    };



    // This item handler will hold our three output slots
    private ItemStackHandler outputHandler = new ItemStackHandler(OUTPUT_SLOTS) {

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return false;
        }

        @Override
        protected void onContentsChanged(int slot) {
            TileCentrifuge.this.markDirty();
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

        isRunning = compound.getBoolean("isrunning");


        rot = compound.getInteger("rot");
        lastrot = compound.getInteger("lastRot");


    //    clientRot = compound.getInteger("clientRot");
    //    lastClientRot = compound.getInteger("lastClientRot");
    }
    //saves stuff in a file so it can be recalled upon boot up
    @Override
    public NBTTagCompound writeToNBT (NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setTag("itemsIn", inputHandler.serializeNBT());
        compound.setTag("itemsOut", outputHandler.serializeNBT());

        compound.setBoolean("isrunning", isRunning);

        compound.setInteger("rot", rot);
        compound.setInteger("lastRot", lastrot);

        compound.setInteger("clientRot", rot);
        compound.setInteger("lastClientRot", lastrot);



        return compound;
    }





    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        lastClientRot = packet.getNbtCompound().getInteger("lastClientRot");
        clientisRunning = packet.getNbtCompound().getBoolean("isrunning");
        clientRot = packet.getNbtCompound().getInteger("clientRot");

    }

    //-------------------------------------------------------------------------------------------------------------



    //-------------------------------------------------------------------------------------------------------------

    public boolean canInteractWith (EntityPlayer playerIn){
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability (Capability< ? > capability, EnumFacing facing){
        IBlockState state = world.getBlockState(pos);
        /*if(state.getBlock() != ModBlocks.blockSolder || state.getValue(BlockSolderTable.FORMED) == SolderPartIndex.UNFORMED){
            return false;
        }*/
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
