package koopamillion.mymod.ballmill;

import koopamillion.mymod.crafting.BallmillManager;
import koopamillion.mymod.crafting.BallmillRecipe;
import koopamillion.mymod.crafting.CentrifugeManager;
import koopamillion.mymod.crafting.CentrifugeRecipe;
import koopamillion.mymod.machineitems.ItemBalls;
import koopamillion.mymod.network.VanillaPackets;
import koopamillion.mymod.simpleblocks.TileAdvancedMotor;
import koopamillion.mymod.simpleblocks.TileMotor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TileBallmill extends TileEntity implements ITickable {


    public static final int INPUT_SLOTS = 2;
    public static final int OUTPUT_SLOTS = 3;
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

    public int bonus = 0;

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

            if(!inputHandler.getStackInSlot(1).isEmpty()){
                if(inputHandler.getStackInSlot(1).getItem() instanceof ItemBalls){
                    bonus = ((ItemBalls) inputHandler.getStackInSlot(1).getItem()).getBonus();
                }
            }

            if(!inputHandler.getStackInSlot(0).isEmpty()){
                if(BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0))!=null){
                    craftRPM = BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getRPM();
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

        if((world.getTileEntity(getPos().down()) instanceof  TileMotor) && !world.isRemote){
            if(((TileMotor) world.getTileEntity(getPos().down())).getRPMproduced() <= MAX_RPM ){
                idleRPM = ((TileMotor) world.getTileEntity(getPos().down())).getRPMproduced();
            }else{
                idleRPM = MAX_RPM;
            }

        }else if(world.getTileEntity(getPos().down()) instanceof TileAdvancedMotor && !world.isRemote){
            if(((TileAdvancedMotor) world.getTileEntity(getPos().down())).getRPMproduced() <= MAX_RPM ) {
                if (world.getTileEntity(getPos().down()) instanceof TileAdvancedMotor)
                    idleRPM = ((TileAdvancedMotor) world.getTileEntity(getPos().down())).getRPMproduced();
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

            List<ItemStack> l = Arrays.asList(BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getInput1().getMatchingStacks());

            inputHandler.extractItem(0, l.get(0).getCount(), false);
            inputHandler.getStackInSlot(1).setItemDamage(inputHandler.getStackInSlot(1).getItemDamage() + 1);


        }
    }








    public boolean shouldruntwo(){
        List<ItemStack> stacks = getResultTwo();
        List<ItemStack> stacker = new ArrayList<>();
        if(inputHandler.getStackInSlot(0).isEmpty() || inputHandler.getStackInSlot(1).isEmpty()) return false;
        if(!(inputHandler.getStackInSlot(1).getItem() instanceof ItemBalls)) return false;

        if(BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)) == null) return false;
        if(!BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput1().isEmpty())
        stacker.add(BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput1());
        if(!BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput2().isEmpty())
        stacker.add(BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput2());
        if(!BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput3().isEmpty())
        stacker.add(BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getOutput3());

        if(stacks == null) return false;
        for (ItemStack stack: stacker) {
            if(insertOutput(stack, true)){

            }else{
                return false;
            }
        }




        if(BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getTier() > ((ItemBalls) inputHandler.getStackInSlot(1).getItem()).getTier()) return false;

        if(idleRPM < BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getRPM()){
            return false;
        }

        List<ItemStack> ll = Arrays.asList(BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getInput1().getMatchingStacks());

        if(!(inputHandler.getStackInSlot(0).getCount() >=(ll.get(0).getCount()))) return false;

        if(BallmillManager.getRecipeForStatsOnly(inputHandler.getStackInSlot(0)).getRPM() > MAX_RPM) return false;
        return true;
    }


    
    public List<ItemStack> getResultTwo(){
        ItemStack stack = inputHandler.getStackInSlot(0);
        BallmillRecipe recipe = BallmillManager.getRecipeForStatsOnly(stack);
        List<ItemStack> finallist = new ArrayList<>();
        int chanced = 0;
        if(recipe == null) return null;

        if(!recipe.getOutput1().isEmpty()){
            if(recipe.getChance1() == 0){
                finallist.add(recipe.getOutput1());
                if(bonus !=0){
                    System.out.println(bonus);//
                    for(int i = 0; i < MathHelper.floor(bonus / 100); i++){
                        finallist.add(new ItemStack(recipe.getOutput1().getItem()));
                    }
                    if(rand.nextInt(100) <= bonus % 100){
                        finallist.add(new ItemStack(recipe.getOutput1().getItem()));
                    }
                }
            }else{

                chanced = recipe.getChance1() * (1+(bonus/100));
                if(chanced > 100) {
                    for (int i = 0; i < MathHelper.floor(chanced / 100); i++) {
                        finallist.add(new ItemStack(recipe.getOutput1().getItem()));
                    }
                    if(rand.nextInt(100) <= chanced % 100){
                        finallist.add(new ItemStack(recipe.getOutput1().getItem()));
                    }
                }else{
                    if(rand.nextInt(100) <= chanced){
                        finallist.add(new ItemStack(recipe.getOutput1().getItem()));
                    }
                }


            }
        }
        if(!recipe.getOutput2().isEmpty()){
            if(recipe.getChance2() == 0){
                finallist.add(recipe.getOutput2());
                if(bonus !=0){
                    for(int i = 0; i < MathHelper.floor(bonus / 100); i++){
                        finallist.add(new ItemStack(recipe.getOutput2().getItem()));
                    }
                    if(rand.nextInt(100) <= bonus % 100){
                        finallist.add(new ItemStack(recipe.getOutput2().getItem()));
                    }
                }
            }else{
                chanced = recipe.getChance2() * (1+(bonus/100));
                if(chanced > 100) {

                    for (int i = 0; i < MathHelper.floor(chanced / 100); i++) {
                        finallist.add(new ItemStack(recipe.getOutput2().getItem()));
                    }
                    if(rand.nextInt(100) <= chanced % 100){
                        finallist.add(new ItemStack(recipe.getOutput2().getItem()));
                    }
                }else{
                    if(rand.nextInt(100) <= chanced){
                        finallist.add(new ItemStack(recipe.getOutput2().getItem()));
                    }
                }
            }
        }
        if(!recipe.getOutput3().isEmpty()){
            if(recipe.getChance3() == 0){
                finallist.add(recipe.getOutput3());
                if(bonus !=0){
                    for(int i = 0; i < MathHelper.floor(bonus / 100); i++){
                        finallist.add(new ItemStack(recipe.getOutput3().getItem()));
                    }
                    if(rand.nextInt(100) <= bonus % 100){
                        finallist.add(new ItemStack(recipe.getOutput3().getItem()));
                    }
                }
            }else{
                chanced = recipe.getChance3() * (1+(bonus/100));
                if(chanced > 100) {
                    for (int i = 0; i < MathHelper.floor(chanced / 100); i++) {
                        finallist.add(new ItemStack(recipe.getOutput3().getItem()));
                    }
                    if(rand.nextInt(100) <= chanced % 100){
                        finallist.add(new ItemStack(recipe.getOutput3().getItem()));
                    }
                }else{
                    if(rand.nextInt(100) <= chanced){
                        finallist.add(new ItemStack(recipe.getOutput3().getItem()));
                    }
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
            TileBallmill.this.markDirty();
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
            TileBallmill.this.markDirty();
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
