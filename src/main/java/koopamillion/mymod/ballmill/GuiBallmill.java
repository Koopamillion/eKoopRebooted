package koopamillion.mymod.ballmill;

import koopamillion.mymod.MyMod;
import koopamillion.mymod.centrifuge.ContainerCentrifuge;
import koopamillion.mymod.centrifuge.TileCentrifuge;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class GuiBallmill extends GuiContainer {

    public static final int WIDTH = 180;
    public static final int HEIGHT = 169;
    private static final ResourceLocation background = new ResourceLocation(MyMod.MODID, "textures/gui/ballmill.png");

    private static final ResourceLocation centrifugeprogressbar = new ResourceLocation(MyMod.MODID, "textures/gui/ballmillprogressbar.png");
    private static final ResourceLocation idle = new ResourceLocation(MyMod.MODID, "textures/gui/idlerpm.png");
    private static final ResourceLocation rot = new ResourceLocation(MyMod.MODID, "textures/gui/rot.png");
    private TileBallmill chicken;




    public GuiBallmill(TileBallmill tileEntity, ContainerBallmill container) {
        super(container);

        xSize = WIDTH;
        ySize = HEIGHT;

        chicken = tileEntity;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        mc.getTextureManager().bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        int i = (int)this.getCookProgressScaled(15);//
        mc.getTextureManager().bindTexture(centrifugeprogressbar);
        //fix this
        if(chicken.getClientProgress() == 0) {
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 82, guiTop + 36, 0, 0, 15, 0, 15, 16);
        }else{
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 82, guiTop + 36, 0, 0, 15 , 16 - i, 15, 16);
        }

        drawIdleBar(chicken.getClientIdleRPM(), chicken.getClientGUIRPM(), chicken.getClientCraftRPM());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);

        List<String> hovering = new ArrayList<>();
        if (mouseX > guiLeft + 9 && mouseX < guiLeft + 62 && mouseY > guiTop + 12 && mouseY < guiTop + 49) {
            hovering.add("Possible RPM: " + chicken.getClientIdleRPM());
            hovering.add("RPM: " + chicken.getClientGUIRPM());
            drawHoveringText(hovering, mouseX, mouseY, fontRenderer);

        }
        hovering.clear();
        if (mouseX > guiLeft + 117 && mouseX < guiLeft + 170 && mouseY > guiTop + 12 && mouseY < guiTop + 49) {
            hovering.add("RPM To craft: " + chicken.getClientCraftRPM());
            hovering.add("RPM: " + chicken.getClientGUIRPM());
            drawHoveringText(hovering, mouseX, mouseY, fontRenderer);
        }

    }

    private double getCookProgressScaled(double pixels)
    {
        int i = (int) chicken.getClientProgress();
        int f = i * 1000;
        int j = f / 40;


        return ((pixels / 100) * j) / 10;
    }

    private void drawIdleBar(int idler, int rote, int crafter) {



        int percentage = idler * 36 / TileBallmill.MAX_RPM;
        for (int i = 0 ; i < percentage ; i++) {
            this.mc.getTextureManager().bindTexture(idle);
            GlStateManager.color(1,1,1,1);
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 10,guiTop + 49 - (i +1),0,0,52,i+1,52f,36f);
        }
        int percentage3 = crafter * 36 / TileBallmill.MAX_RPM;
        for (int i = 0 ; i < percentage3 ; i++) {
            this.mc.getTextureManager().bindTexture(idle);
            GlStateManager.color(1,1,1,1);
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 118,guiTop + 49 - (i +1),0,0,52,i+1,52f,36f);
        }
        int percentage2 = rote * 36 / TileBallmill.MAX_RPM;
        for (int i = 0 ; i < percentage2 ; i++) {
            this.mc.getTextureManager().bindTexture(rot);
            GlStateManager.color(1,1,1,1);
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 10,guiTop + 49 - (i +1),0,0,52,i+1,52f,36f);
            Gui.drawModalRectWithCustomSizedTexture(guiLeft + 118,guiTop + 49 - (i +1),0,0,52,i+1,52f,36f);
        }

    }



}
