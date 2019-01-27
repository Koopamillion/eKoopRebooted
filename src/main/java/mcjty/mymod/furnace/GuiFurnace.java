package mcjty.mymod.furnace;


import mcjty.mymod.MyMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;



import java.util.Random;


public class GuiFurnace extends GuiContainer {
    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;

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

        System.out.println(furnace.getGuiTime());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }
}


