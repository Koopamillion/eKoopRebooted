package koopamillion.mymod.soldertable;

import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.solder.BlockSolder;
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
    public static final float TANK_THICKNESS = 0.01F;
    public static final float randomscale = 0.360F;
    public static final float randomscale2 = 0.6875f;
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
       // renderTest(tileEntity);
        renderFluid(tileEntity);

        GlStateManager.popMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
    }


    private void renderTest(TileSolderTable tank){
        FluidStack fluid = tank.getTank().getFluid();
        Fluid renderFluid = fluid.getFluid();
        float scale = (0.370F - TANK_THICKNESS/2 - TANK_THICKNESS) * fluid.amount / (tank.getTank().getCapacity());

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        ResourceLocation still = renderFluid.getStill();
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(still.toString());

        float u1 = sprite.getMinU();
        float v1 = sprite.getMinV();
        float u2 = sprite.getMaxU();
        float v2 = sprite.getMaxV();

        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

        GlStateManager.color(1, 1, 1, .5f);

        renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        //    randomscale = 0.25f;
        //top
        renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

        // Bottom
        renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

        // Sides
        renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u1, v1).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();

        renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

        renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

        renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
        renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
        tessellator.draw();

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

        //isnt scales fault isnt the if statements fault, is being called

       float scale = (0.370F - TANK_THICKNESS/2 - TANK_THICKNESS) * fluid.amount / (tank.getTank().getCapacity());
       // float scale = (0.370F - TANK_THICKNESS/2 - TANK_THICKNESS) * 3000 / 4000;
        // float scale = (1.0f - TANK_THICKNESS/2 - TANK_THICKNESS) * 2000 / 4000));

        //  if (tank.getPos() == )
        // if()
        if (scale > 0.0f) {

            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder renderer = tessellator.getBuffer();
            ResourceLocation still = renderFluid.getStill();
            TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(still.toString());

            float u1 = sprite.getMinU();
            float v1 = sprite.getMinV();
            float u2 = sprite.getMaxU();
            float v2 = sprite.getMaxV();

            net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

            GlStateManager.color(1, 1, 1, .5f);




            // Top
            if(tank.getBlockState().getBlock() == ModBlocks.blockSolder) {
//this crashes upon respawn in multiplayer
                if (tank.getBlockState().getValue(BlockSolderTable.FACING).getHorizontalIndex() == EnumFacing.NORTH.getHorizontalIndex()) {
                   //
                    renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                    //    randomscale = 0.25f;
                    //top
                    renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    // Bottom
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                    // Sides
                    renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    tessellator.draw();
                } else if (tank.getBlockState().getValue(BlockSolderTable.FACING).getHorizontalIndex() == EnumFacing.SOUTH.getHorizontalIndex()) {
                    // randomscale = 0.6875f;
                    renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                    //top
                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, 1F - TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    // Bottom
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, 1F - TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    // Sides
                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, 1F - TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, 1F - TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, 1F - TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    tessellator.draw();
                } else if (tank.getBlockState().getValue(BlockSolderTable.FACING).getHorizontalIndex() == EnumFacing.EAST.getHorizontalIndex()) {
                    renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    // Bottom
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    // Sides
                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, TANK_THICKNESS + randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, 0.6875, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, 0.6875, TANK_THICKNESS + randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(1F - TANK_THICKNESS - randomscale, scale + 0.6875, TANK_THICKNESS + randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    tessellator.draw();
                } else if (tank.getBlockState().getValue(BlockSolderTable.FACING).getHorizontalIndex() == EnumFacing.WEST.getHorizontalIndex()) {
                    renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
                    //top

                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    // Bottom
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    // Sides
                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, 1F - TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, 0.6875, 1F - TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS, scale + 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();

                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, 1F - TANK_THICKNESS).tex(u1, v1).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, 1F - TANK_THICKNESS).tex(u1, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v2).color(255, 255, 255, 128).endVertex();
                    renderer.pos(TANK_THICKNESS + randomscale, scale + 0.6875, 1F - TANK_THICKNESS - randomscale).tex(u2, v1).color(255, 255, 255, 128).endVertex();
                    tessellator.draw();
                } else {
                    return;
                }
            }
            net.minecraft.client.renderer.RenderHelper.enableStandardItemLighting();
        }
    }
}