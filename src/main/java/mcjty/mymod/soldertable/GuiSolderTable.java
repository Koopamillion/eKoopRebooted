package mcjty.mymod.soldertable;

import mcjty.mymod.MyMod;
import mcjty.mymod.plushy.ContainerChickenPlushy;
import mcjty.mymod.plushy.TileChickenPlushy;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiSolderTable extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 152;
    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/solder.png");
    private TileSolderTable solder;




    public GuiSolderTable(TileSolderTable tileEntity, ContainerSolderTable container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        solder = tileEntity;
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
