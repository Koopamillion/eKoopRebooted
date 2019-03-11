package koopamillion.mymod.plushy;

import koopamillion.mymod.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.util.ResourceLocation;

public class PlushyTESR extends TileEntitySpecialRenderer<TileChickenPlushy> {

    private static final ResourceLocation CHICKEN_TEXTURES = new ResourceLocation("textures/entity/chicken.png");
    private static final ModelChicken model = new ModelChicken();
    private static EntityChicken entity;


    @Override
    public void render(TileChickenPlushy tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {


        //bind it in block chicken plushy to work

        if (!tileEntity.hasWorld()) {
            return;
        }


        super.render(tileEntity, x, y, z, partialTicks, destroyStage, alpha);

        IBlockState state = tileEntity.getWorld().getBlockState(tileEntity.getPos());
        if (state.getBlock() != ModBlocks.blockChicken) { // I don't know. But it seems for some reason the renderer gets called for minecraft:air in certain cases.
            return;
        }

        if (entity == null && tileEntity.hasWorld()) {
            entity = new EntityChicken(tileEntity.getWorld());
            entity.setScaleForAge(true);
        }

        bindTexture(CHICKEN_TEXTURES);
        if (entity != null) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5f, y, z + 0.5f);
           // GlStateManager.rotate(0, 0f, 0f, 1f);
            GlStateManager.rotate(180f, 0f, 0f, 0f);
            GlStateManager.scale(0.1f, 0.1f, 0.1f);
            model.render(entity, 0f, 0f, 0f, 0f, 0f, 1f);
            GlStateManager.popMatrix();
        }
    }
}
