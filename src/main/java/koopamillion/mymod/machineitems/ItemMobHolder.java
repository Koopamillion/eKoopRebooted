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
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
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




    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        String name;
        int dnacount = 0;
        NBTTagCompound tag = stack.getTagCompound();
        if (containsEntity(stack)) {
            dnacount = stack.getTagCompound().getInteger("dna");
            try {
                String entityName = tag.getString("entity");
                name = Class.forName(entityName).getSimpleName();
            }catch(ClassNotFoundException e){
                name = "Unknown";
            }
            tooltip.add("Mob: " + name);
            tooltip.add("DNA: " + dnacount);
        }

    }
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
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
    }


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
