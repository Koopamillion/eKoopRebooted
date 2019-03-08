package koopamillion.mymod.saturator;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.acidbath.TileAcidBath;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderItemFrame;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.Random;

public class SaturatorTESR extends TileEntitySpecialRenderer<TileSaturator> {
    public static final float TANK_THICKNESS = 0.0125f;

    public  EntityItem entityItem = null;

    private float time = 0;
    public TileSaturator tilee;

    Random rand = new Random();
    public SaturatorTESR() {
    }

    @Override
    public void render(TileSaturator te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {


        tilee = te;
        renderItemStack(te, Minecraft.getMinecraft().world,x,y,z, partialTicks);
        GlStateManager.enableBlend();
    }






    public void renderItemStack(TileSaturator te,World world,double x, double y, double z, float ticks){
        if(getStack(te)!=null) {

            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.disableBlend();
            time = te.getWorld().getTotalWorldTime() +ticks;
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + 0.5, y + 1.0, z + 0.5);
            GlStateManager.scale(1.1f, 1.1f, 1.1f);
            GlStateManager.translate(0, Math.sin(time/8)/8, 0);
            GlStateManager.rotate(time,0,1,0);
            Minecraft.getMinecraft().getRenderItem().renderItem(getStack(te), ItemCameraTransforms.TransformType.GROUND);

            GlStateManager.popMatrix();



        }
        }

    public ItemStack getStack(TileSaturator te){

        for(int i = 0; i < TileSaturator.INPUT_SLOTS; i++){
            if(te.getInputHandler().getStackInSlot(i).getItem() instanceof ItemFood){
                return te.getInputHandler().getStackInSlot(i);

            }
        }
        return null;
    }
}