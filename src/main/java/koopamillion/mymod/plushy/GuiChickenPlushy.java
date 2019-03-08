package koopamillion.mymod.plushy;

import koopamillion.mymod.MyMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;



public class GuiChickenPlushy extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;
    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/chicken.png");
    private TileChickenPlushy chicken;




    public GuiChickenPlushy(TileChickenPlushy tileEntity, ContainerChickenPlushy container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        chicken = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);

    }




}
