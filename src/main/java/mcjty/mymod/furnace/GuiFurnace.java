package mcjty.mymod.furnace;


import mcjty.mymod.ModBlocks;
import mcjty.mymod.MyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;


import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Random;

import static net.minecraft.client.Minecraft.getMinecraft;
import static org.lwjgl.opengl.GL11.glColor3f;


public class GuiFurnace extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;
    private static final ResourceLocation FurnaceTextures = new ResourceLocation(MyMod.MODID,"textures/gui/furnacepoweredbar.png");
    private static final ResourceLocation FurnaceDead = new ResourceLocation(MyMod.MODID,"textures/gui/furnacedeadpowerbar.png");
    private static final ResourceLocation LOCATION_BLOCKS_TEXTURE = new ResourceLocation(MyMod.MODID,"textures/blocks/redstone_block.png");
    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/furnace.png");
    private TileFurnace furnace;
    Random rand = new Random();
    private int rgb = 0;


    public void randomPicker (int bound){

        int r = rand.nextInt(bound);

        int g = rand.nextInt(bound);

        int b = rand.nextInt(bound);

        rgb = (r << 16)|(g << 8)|(b);
    }




    public GuiFurnace(TileFurnace tileEntity, ContainerFurnace container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        furnace = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        int energy = furnace.getClientEnergy();
        drawEnergyBar(energy);

        if (furnace.getClientProgress() != 0.000000000000000f){
            if (furnace.getGuiTime() >= 0.15f){
                float percentage = furnace.getClientProgress() / furnace.getGuiTime();
                drawString(mc.fontRenderer, "Time remaining: " + Math.round(percentage * 100) + "%", guiLeft + 10, guiTop + 50, 0xffffff);
            }else{
                randomPicker(255);
                drawString(mc.fontRenderer, "M", guiLeft + 10, guiTop + 50, rgb);
                randomPicker(255);
                drawString(mc.fontRenderer, "  a", guiLeft + 10, guiTop + 50, rgb);
                randomPicker(255);
                drawString(mc.fontRenderer, "    x", guiLeft + 10, guiTop + 50, rgb);
                randomPicker(255);
                drawString(mc.fontRenderer, "       S", guiLeft + 10, guiTop + 50, rgb);
                randomPicker(255);
                drawString(mc.fontRenderer, "         p", guiLeft + 10, guiTop + 50, rgb);
                randomPicker(255);
                drawString(mc.fontRenderer, "           e", guiLeft + 10, guiTop + 50, rgb);
                randomPicker(255);
                drawString(mc.fontRenderer, "             e", guiLeft + 10, guiTop + 50, rgb);
                randomPicker(255);
                drawString(mc.fontRenderer, "               d", guiLeft + 10, guiTop + 50, rgb);
            }

           // System.out.println(furnace.getProgressRemaining());

        }else if (furnace.getGuiTime() >=0.15f){
            drawString(mc.fontRenderer, "Time remaining:", guiLeft + 10, guiTop + 50, 0xffffff);

        }else{
            randomPicker(255);
            drawString(mc.fontRenderer, "M", guiLeft + 10, guiTop + 50, rgb);
            randomPicker(255);
            drawString(mc.fontRenderer, "  a", guiLeft + 10, guiTop + 50, rgb);
            randomPicker(255);
            drawString(mc.fontRenderer, "    x", guiLeft + 10, guiTop + 50, rgb);
            randomPicker(255);
            drawString(mc.fontRenderer, "       S", guiLeft + 10, guiTop + 50, rgb);
            randomPicker(255);
            drawString(mc.fontRenderer, "         p", guiLeft + 10, guiTop + 50, rgb);
            randomPicker(255);
            drawString(mc.fontRenderer, "           e", guiLeft + 10, guiTop + 50, rgb);
            randomPicker(255);
            drawString(mc.fontRenderer, "             e", guiLeft + 10, guiTop + 50, rgb);
            randomPicker(255);
            drawString(mc.fontRenderer, "               d", guiLeft + 10, guiTop + 50, rgb);
        }

        //System.out.println(furnace.getGuiTime());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);


        if (mouseX > guiLeft + 10 && mouseX < guiLeft + 112 && mouseY > guiTop + 5 && mouseY < guiTop + 15) {
            drawHoveringText(Collections.singletonList("Energy: " + furnace.getClientEnergy()), mouseX, mouseY, fontRenderer);
        }
    }



    private void drawEnergyBar(int energy) {
        this.mc.getTextureManager().bindTexture(FurnaceDead);
        Gui.drawModalRectWithCustomSizedTexture(guiLeft + 10,guiTop + 5,0,0,99,14,100f,14f);
        int percentage = energy * 100 / TileFurnace.MAX_POWER;
        for (int i = 0 ; i < percentage ; i++) {

         //   TextureAtlasSprite sprite = this.mc.getBlockRendererDispatcher().getBlockModelShapes().getTexture(Blocks.FLOWING_LAVA.getDefaultState());
            this.mc.getTextureManager().bindTexture(FurnaceTextures);

         //   Minecraft.getMinecraft().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
            GlStateManager.color(1,1,1,1);
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 10,guiTop + 5,0,0,i,14,100f,14f);
         //   this.drawTexturedModalRect(guiLeft + 10,guiTop + 3,sprite,i,14);


//code greyed out is for fluid in guid


        }
    }

}



