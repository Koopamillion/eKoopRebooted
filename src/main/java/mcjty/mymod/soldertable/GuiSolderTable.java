package mcjty.mymod.soldertable;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import mcjty.mymod.ModBlocks;
import mcjty.mymod.MyMod;
import mcjty.mymod.furnace.TileFurnace;
import mcjty.mymod.plushy.ContainerChickenPlushy;
import mcjty.mymod.plushy.TileChickenPlushy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

public class GuiSolderTable extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/solder.png");
    private static final ResourceLocation fluidgauge = new ResourceLocation(MyMod.MODID, "textures/gui/fluidgauge.png");
    private static final ResourceLocation solderprogressbar = new ResourceLocation(MyMod.MODID, "textures/gui/solderprogressbar.png");

    private TileSolderTable solder;
  //  private int j = 0;




    public GuiSolderTable(TileSolderTable tileEntity, ContainerSolderTable container) {
        super(container);


        xSize = WIDTH;
        ySize = HEIGHT;

        solder = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);

        int fluid = solder.getClientFluidAmount();
        int capacity3 = solder.getCapacity();
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        int l = (int)this.getCookProgressScaled(15);//
        // int k = (int)(l / 10);

        mc.getTextureManager().bindTexture(solderprogressbar);
        if(solder.getClientProgress() == 0) {
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 121, guiTop + 26, 0, 0, 0, 16, 15, 16);
        }else{
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 121, guiTop + 26, 0, 0, 15 - l, 16, 15, 16);
        }
        drawFluidBar(fluid, capacity3);
        mc.getTextureManager().bindTexture(fluidgauge);
        Gui.drawModalRectWithCustomSizedTexture(guiLeft + 10,guiTop + 11,0,0,16,45, 16, 45);

     //   drawString(mc.fontRenderer, "Fluid: " + fluid, guiLeft + 10, guiTop + 50, 0xffffff);
    }

    private double getCookProgressScaled(double pixels)
    {
        int i = (int) solder.getClientProgress();
        int f = i * 1000;
        int j = f / 40;


        return ((pixels / 100) * j) / 10;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
        if (mouseX > guiLeft + 9 && mouseX < guiLeft + 26 && mouseY > guiTop + 10 && mouseY < guiTop + 56) {
            if(solder.getClientFluidAmount() == 0){
                drawHoveringText(Collections.singletonList("0 mb of Solder"), mouseX, mouseY, fontRenderer);

            }else{
                drawHoveringText(Collections.singletonList(solder.getClientFluidAmount() + "mb of Solder"), mouseX, mouseY, fontRenderer);}
        }

    }
    private void drawFluidBar(float fluidamount, int capacity2) {
        TextureAtlasSprite sprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(ModBlocks.blockSolderFluid.getDefaultState());
        float amount = fluidamount;
        float capacity = (float) capacity2;
        float scale = amount / capacity;
        int fluidTankHeight = 150;
        int fluidAmount = (int) fluidamount; //added code
       // int fluidAmount = (int) (scale * fluidTankHeight);
        float level = fluidAmount / capacity;
        Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        float minU = sprite.getInterpolatedU(0);
        float minV = sprite.getInterpolatedV(0);
        float maxU = sprite.getInterpolatedU(16);
        float maxV = sprite.getInterpolatedV(16);
     //   int filled = Math.min((Math.round((level * height)*(int) (capacity / 1000))), height);
        int filled = Math.min((int)(level * height), height); //added code
        filled = filled / 6; //added code

        for (int i = 0; i < filled / 16; i++) {
            drawFluidQuad(guiLeft + 10, (guiTop + 20) + (i * 16) + (36 - (filled)) , 16, 16, minU, minV, maxU, maxV);
        }
        if (filled % 16 != 0) {
            drawFluidQuad(guiLeft + 10, (guiTop + 20) + (36 - (filled % 16)), 16, filled % 16, minU, minV, maxU, sprite.getInterpolatedV(filled % 16));
        }
    }

       private void drawFluidQuad(int x, int y, int width, int height, float minU, float minV, float maxU, float maxV) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        //bottom left
        buffer.pos((double)x, ((double)(y + height)), (double)this.zLevel).tex(minU, maxV).endVertex();
        //bottom right
        buffer.pos((double)(x + width), ((double)(y) + height), (double)this.zLevel).tex(maxU, maxV).endVertex();
        //top right
        buffer.pos((double)(x + width), (double)y, (double)this.zLevel).tex(maxU, minV).endVertex();
        //top left
        buffer.pos((double)x, (double)y, (double)this.zLevel).tex(minU, minV).endVertex();
        tessellator.draw();
    }

}
