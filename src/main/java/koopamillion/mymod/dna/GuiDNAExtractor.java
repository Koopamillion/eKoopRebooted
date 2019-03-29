package koopamillion.mymod.dna;

import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.soldertable.ContainerSolderTable;
import koopamillion.mymod.soldertable.TileSolderTable;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import java.util.Collections;

public class GuiDNAExtractor  extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 169;
    private static final int increase = 17;
    private IBlockState texture;


    // public int


    private static final ResourceLocation FurnaceTextures = new ResourceLocation(MyMod.MODID,"textures/gui/furnacepoweredbar.png");
    private static final ResourceLocation FurnaceDead = new ResourceLocation(MyMod.MODID,"textures/gui/furnacedeadpowerbar.png");

    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/solder.png");

    private static final ResourceLocation solderprogressbar = new ResourceLocation(MyMod.MODID, "textures/gui/solderprogressbar.png");

    private TileDNAExtractor solder;
    //  private int j = 0;




    public GuiDNAExtractor(TileDNAExtractor tileEntity, ContainerDNAExtractor container) {
        super(container);


        xSize = WIDTH;
        ySize = HEIGHT;

        solder = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);


        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        int l = (int)this.getCookProgressScaled(15);//
        // int k = (int)(l / 10);

        mc.getTextureManager().bindTexture(solderprogressbar);
        if(solder.getClientProgress() == 0) {
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 121, guiTop + 26 + increase, 0, 0, 0, 16, 15, 16);
        }else{
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 121, guiTop + 26 + increase, 0, 0, 15 - l, 16, 15, 16);
        }

        String name;
        try {
            String entityName = solder.getClienttag().getString("mobType");
            name = Class.forName(entityName).getSimpleName();
        }catch(ClassNotFoundException e){
            name = "Unknown";
        }
        drawString(mc.fontRenderer, "Mob: " + name, guiLeft + 10, guiTop + 50, 0xffffff);
        drawString(mc.fontRenderer, "DNA: " + solder.getClientDNA(), guiLeft + 10, guiTop + 60, 0xffffff);

        int energy = solder.getClientEnergy();
        drawEnergyBar(energy);


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

        if (mouseX > guiLeft + 9 && mouseX < guiLeft + 109 && mouseY > guiTop + 4 && mouseY < guiTop + 19) {
            drawHoveringText(Collections.singletonList("Energy: " + solder.getClientEnergy()), mouseX, mouseY, fontRenderer);
        }


    }

    private void drawEnergyBar(int energy) {
        this.mc.getTextureManager().bindTexture(FurnaceDead);
        Gui.drawModalRectWithCustomSizedTexture(guiLeft + 10,guiTop + 5,0,0,99,14,100f,14f);
        int percentage = energy * 100 / TileDNAExtractor.MAX_POWER;
        for (int i = 0 ; i < percentage ; i++) {
            this.mc.getTextureManager().bindTexture(FurnaceTextures);
            GlStateManager.color(1,1,1,1);
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 10,guiTop + 5,0,0,i,14,100f,14f);
        }
    }

}
