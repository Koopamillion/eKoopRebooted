package koopamillion.mymod.saturator;

import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.soldertable.ContainerSolderTable;
import koopamillion.mymod.soldertable.TileSolderTable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

public class GuiSaturator extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 169;
    private static final int increase = 17;
    // public int


    private static final ResourceLocation FurnaceTextures = new ResourceLocation(MyMod.MODID,"textures/gui/furnacepoweredbar.png");
    private static final ResourceLocation FurnaceDead = new ResourceLocation(MyMod.MODID,"textures/gui/furnacedeadpowerbar.png");
    private static final ResourceLocation HeatBar = new ResourceLocation(MyMod.MODID,"textures/gui/heatbar.png");
    private static final ResourceLocation GlassHeatBar = new ResourceLocation(MyMod.MODID,"textures/gui/glassheatbar.png");
    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/solder.png");
    private static final ResourceLocation fluidgauge = new ResourceLocation(MyMod.MODID, "textures/gui/fluidgauge.png");
    private static final ResourceLocation solderprogressbar = new ResourceLocation(MyMod.MODID, "textures/gui/solderprogressbar.png");

    private TileSaturator solder;
    //  private int j = 0;




    public GuiSaturator(TileSaturator tileEntity, ContainerSaturator container) {
        super(container);


        xSize = WIDTH;
        ySize = HEIGHT;

        solder = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);


        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);




        int energy = solder.getClientEnergy();

        drawEnergyBar(energy);



    }


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);

        if (mouseX > guiLeft + 9 && mouseX < guiLeft + 109 && mouseY > guiTop + 4 && mouseY < guiTop + 19) {
            drawHoveringText(Collections.singletonList("Energy: " + solder.getClientEnergy()), mouseX, mouseY, fontRenderer);
        }


    }

    private void drawEnergyBar(int energy) {
        this.mc.getTextureManager().bindTexture(FurnaceDead);
        Gui.drawModalRectWithCustomSizedTexture(guiLeft + 10,guiTop + 5,0,0,99,14,100f,14f);
        int percentage = energy * 100 / TileSaturator.MAX_POWER;
        for (int i = 0 ; i < percentage ; i++) {
            this.mc.getTextureManager().bindTexture(FurnaceTextures);
            GlStateManager.color(1,1,1,1);
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 10,guiTop + 5,0,0,i,14,100f,14f);
        }
    }


}
