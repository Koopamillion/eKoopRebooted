package koopamillion.mymod.machineitems;

import koopamillion.mymod.MyMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import scala.Int;

import javax.annotation.Nullable;
import java.util.List;

public class ItemMobHolder extends Item {
    public ItemMobHolder(){
        this.setCreativeTab(MyMod.tabEKoop);
        setRegistryName("mobholder");        // The unique name (within your mod) that identifies this item
        setTranslationKey(MyMod.MODID + ".mobholder");     // Used for localization (en_US.lang)
        setMaxStackSize(1);
    }

    public boolean containsEntity(ItemStack stack){
        return !stack.isEmpty() && stack.hasTagCompound() && stack.getTagCompound().hasKey("entity") && stack.getTagCompound().hasKey("dna") && stack.getTagCompound().getInteger("dna") > 0;
    }

    private int timer = 9;




    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        String name;
        int dnacount = 0;
        try{
            String s = tooltip.get(1);
            String ss = tooltip.get(2);
        }catch(Exception e){
            tooltip.add(1, "Mob:");
            tooltip.add(2, "DNA:");
        }

        NBTTagCompound tag = stack.getTagCompound();
        if (containsEntity(stack)) {
            dnacount = stack.getTagCompound().getInteger("dna");
            try {
                String entityName = tag.getString("entity");
                name = Class.forName(entityName).getSimpleName();
            }catch(ClassNotFoundException e){
                name = "Unknown";
            }
            tooltip.set(1, "Mob: "+name);
            tooltip.set(2, "DNA: " + dnacount);
        }else{
            tooltip.set(1, "Mob:");
            tooltip.set(2, "DNA:");
        }

    }
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if(!entityIn.isSneaking()){
            timer = 9;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(worldIn.isRemote) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        if(playerIn.getHeldItem(handIn).hasTagCompound()){
            if(playerIn.getHeldItem(handIn).getTagCompound().getString("entity").isEmpty()) return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }else{
            return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(handIn));
        }
        if(playerIn.isSneaking()){

            timer--;
            if(timer == 1){
                playerIn.sendStatusMessage(new TextComponentString(TextFormatting.RED + "The next click will erase, are you sure?"),false);
            }else{
                if(timer > 0)
                    playerIn.sendStatusMessage(new TextComponentString(TextFormatting.YELLOW + "Right clicks to erase: " + timer),false);
            }

        }
        if(timer <= 0){
            if(playerIn.getHeldItem(handIn).hasTagCompound()){
                playerIn.getHeldItem(handIn).setTagCompound(new NBTTagCompound());
                playerIn.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "Erased"),false);
                timer = 9;
                return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
            }

        }
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

  /*  @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (player.getEntityWorld().isRemote) return EnumActionResult.FAIL;
        if (!containsEntity(stack)) return EnumActionResult.FAIL;

        Entity entity = createEntity(player, worldIn, stack.getTagCompound().getString("entity"));
        if(entity == null) return EnumActionResult.FAIL;
        BlockPos blockPos = pos.offset(facing);
        entity.setPositionAndRotation(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5, 0, 0);
        player.swingArm(hand);
        //
        player.setHeldItem(hand, stack);
        worldIn.spawnEntity(entity);
        stack.getTagCompound().setInteger("dna", stack.getTagCompound().getInteger("dna") - 1);
        if (entity instanceof EntityLiving) ((EntityLiving) entity).playLivingSound();
        if(stack.getTagCompound().getInteger("dna") == 1){
            stack.setTagCompound(new NBTTagCompound());
        }
        return EnumActionResult.SUCCESS;
    }*/


    private EntityLivingBase createEntity(EntityPlayer player, World world, String type) {
        EntityLivingBase entityLivingBase;
        try {
            entityLivingBase = (EntityLivingBase) Class.forName(type).getConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            entityLivingBase = null;
        }
        return entityLivingBase;
    }

}
