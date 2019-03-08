package koopamillion.mymod.acidbath;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

public class AcidTESR extends TileEntitySpecialRenderer<TileAcidBath> {
    public static final float TANK_THICKNESS = 0.0125f;

    public AcidTESR() {
    }

    @Override
    public void render(TileAcidBath tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        GlStateManager.pushMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.disableBlend();
        GlStateManager.translate((float) x, (float) y, (float) z);

        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        renderFluid(tileEntity);

        GlStateManager.popMatrix();
    }

    private void renderFluid(TileAcidBath tank) {
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

        float scale = (1.0f - 0.16f/2 - 0.16f) * fluid.amount / (tank.getTank().getCapacity());

        if (scale > 0.0f) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder renderer = tessellator.getBuffer();
            ResourceLocation still = renderFluid.getStill();
            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(still.toString());

            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            GlStateManager.color(1, 1, 1, .5f);
            renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

            float u1 = sprite.getMinU();
            float v1 = sprite.getMinV();
            float u2 = sprite.getMaxU();
            float v2 = sprite.getMaxV();

            // Top
            renderer.pos(TANK_THICKNESS, scale + 0.19, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, scale + 0.19, 1-TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, scale + 0.19, 1-TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, scale + 0.19, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

            // Bottom
            renderer.pos(1-TANK_THICKNESS, 0.19, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, 0.19, 1-TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, 0.19, 1-TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, 0.19, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

            // Sides
            renderer.pos(TANK_THICKNESS, scale + 0.19, 1-TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, 0.19, 1-TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, 0.19, 1-TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, scale + 0.19, 1-TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

            renderer.pos(1-TANK_THICKNESS, scale + 0.19, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, 0.19, TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, 0.19, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, scale + 0.19, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

            renderer.pos(1-TANK_THICKNESS, scale + 0.19, 1-TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, 0.19, 1-TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, 0.19, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(1-TANK_THICKNESS, scale + 0.19, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

            renderer.pos(TANK_THICKNESS, scale + 0.19, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, 0.19, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, 0.19, 1-TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
            renderer.pos(TANK_THICKNESS, scale + 0.19, 1-TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

            tessellator.draw();

            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        }
    }
}
