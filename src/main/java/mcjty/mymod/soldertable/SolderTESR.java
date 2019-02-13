package mcjty.mymod.soldertable;

import mcjty.mymod.solder.BlockSolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class SolderTESR extends TileEntitySpecialRenderer<TileSolderTable> {
    public static final float TANK_THICKNESS = 1F / 16F;
    public static final float randomscale = 0.25f;
    private BlockSolder block;

    public SolderTESR(){}

    @Override
    public void render(TileSolderTable tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.translate((float) x, (float) y, (float) z);

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderFluid(tileEntity);

        GlStateManager.popMatrix();
    }

    private void renderFluid(TileSolderTable tank) {
        if (tank == null) {
            return;
        }

        FluidStack fluid = tank.getTank().getFluid();
        if (fluid == null) {
            return;
        }

        Fluid renderFluid = fluid.getFluid();
        if (renderFluid == null) {
            return;
        }

        float scale = (1.0f - TANK_THICKNESS/2 - TANK_THICKNESS) * fluid.amount / (tank.getTank().getCapacity());
       // float scale = (1.0f - TANK_THICKNESS/2 - TANK_THICKNESS) * 2000 / 4000));

        if (scale > 0.0f) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder renderer = tessellator.getBuffer();
            ResourceLocation still = renderFluid.getStill();
            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(still.toString());

            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            GlStateManager.color(1, 1, 1, .5f);


            float u1 = sprite.getMinU();
            float v1 = sprite.getMinV();
            float u2 = sprite.getMaxU();
            float v2 = sprite.getMaxV();

            // Top

            if(tank.getBlockState().getValue(BlockSolderTable.FACING).getHorizontalIndex() == EnumFacing.NORTH.getHorizontalIndex()) {
                renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                //top
                renderer.pos(TANK_THICKNESS, scale + TANK_THICKNESS, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS, scale + TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS + randomscale, scale + TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS + randomscale, scale + TANK_THICKNESS, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                // Bottom
                renderer.pos(TANK_THICKNESS + randomscale, TANK_THICKNESS, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS + randomscale, TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS, TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS, TANK_THICKNESS, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                // Sides
                renderer.pos(TANK_THICKNESS, scale + TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS, TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS + randomscale, TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS + randomscale, scale + TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                renderer.pos(TANK_THICKNESS + randomscale, scale + TANK_THICKNESS, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS + randomscale, TANK_THICKNESS, TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS, TANK_THICKNESS, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS, scale + TANK_THICKNESS, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                renderer.pos(TANK_THICKNESS + randomscale, scale + TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS + randomscale, TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS + randomscale, TANK_THICKNESS, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS + randomscale, scale + TANK_THICKNESS, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                renderer.pos(TANK_THICKNESS, scale + TANK_THICKNESS, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS, TANK_THICKNESS, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS, TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                renderer.pos(TANK_THICKNESS, scale + TANK_THICKNESS, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                tessellator.draw();
            }else{
                return;
            }
            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        }
    }
}
