package koopamillion.mymod.plushy;

import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.plugins.BasePlugin;
import koopamillion.mymod.soldertable.BlockSolderTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityMobSpawnerRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class PlushyTESR extends TileEntitySpecialRenderer<TileChickenPlushy> {

    private static final ResourceLocation CHICKEN_TEXTURES = new ResourceLocation("minecraft","textures/entity/chicken.png");
    public ResourceLocation TEXTURES = new ResourceLocation("minecraft", "textures/entity/");
    private static final ModelChicken model = new ModelChicken();
    private static EntityChicken entity;
    private static EntityLivingBase entityLivingBase;


    @Override
    public void render(TileChickenPlushy tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {


        //bind it in block chicken plushy to work

        if (!tileEntity.hasWorld()) {
            return;
        }

        NBTTagCompound tag = tileEntity.getNbt();



       entityLivingBase = createEntity(getWorld(), tag.getString("type"));



        //    System.out.println(tag);



        super.render(tileEntity, x, y, z, partialTicks, destroyStage, alpha);

        IBlockState state = tileEntity.getWorld().getBlockState(tileEntity.getPos());
        if (state.getBlock() != ModBlocks.blockChicken) {
            return;
        }


        if(!(tileEntity.getBlockState().getBlock() == ModBlocks.blockChicken)) {
            return;
        }




      //  bindTexture(CHICKEN_TEXTURES);
        if (entityLivingBase != null) {
            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.disableBlend();
            GlStateManager.pushMatrix();
            if(entityLivingBase instanceof EntitySquid){
                GlStateManager.translate(x + 0.5f, y+0.5f+0.0625F, z + 0.5f);
            }else{
                GlStateManager.translate(x + 0.5f, y+0.0625F, z + 0.5f);
            }

           // GlStateManager.rotate(0, 0f, 0f, 1f);
            if (tileEntity.getBlockState().getValue(BlockSolderTable.FACING).getHorizontalIndex() == EnumFacing.NORTH.getHorizontalIndex()){
                GlStateManager.rotate(180F, 0f, 1f, 0f);
            }
            if (tileEntity.getBlockState().getValue(BlockSolderTable.FACING).getHorizontalIndex() == EnumFacing.EAST.getHorizontalIndex()){
                GlStateManager.rotate(90F, 0f, 1f, 0f);
            }
            if (tileEntity.getBlockState().getValue(BlockSolderTable.FACING).getHorizontalIndex() == EnumFacing.SOUTH.getHorizontalIndex()){
                GlStateManager.rotate(0F, 0f, 1f, 0f);
            }
            if (tileEntity.getBlockState().getValue(BlockSolderTable.FACING).getHorizontalIndex() == EnumFacing.WEST.getHorizontalIndex()){
                GlStateManager.rotate(270F, 0f, 1f, 0f);
            }
            if(entityLivingBase instanceof EntityGhast){
                GlStateManager.scale(0.3f, 0.3f, 0.3f);
            }else if(entityLivingBase instanceof EntityDragon){
                GlStateManager.scale(0.2f, 0.2f, 0.2f);
            }else{
                GlStateManager.scale(0.4f, 0.4f, 0.4f);
            }

        //    model.render(entity, 0f, 0f, 0f, 0f, 0f, 1f);
            Minecraft.getMinecraft().getRenderManager().renderEntity(entityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 0, false);
            GlStateManager.popMatrix();
            GlStateManager.enableBlend();
        }
    }

    private EntityLivingBase createEntity(World world, String type) {
        EntityLivingBase entityLivingBase;
        try {
            entityLivingBase = (EntityLivingBase) Class.forName(type).getConstructor(World.class).newInstance(world);
        } catch (Exception e) {
            entityLivingBase = null;
        }
        return entityLivingBase;
    }
}
