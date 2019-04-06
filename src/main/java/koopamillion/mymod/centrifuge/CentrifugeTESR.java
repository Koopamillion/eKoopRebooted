package koopamillion.mymod.centrifuge;

import koopamillion.mymod.MyMod;
import koopamillion.mymod.tools.ModelsCache;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.property.Properties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import scala.collection.parallel.ParIterableLike;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CentrifugeTESR extends TileEntitySpecialRenderer<TileCentrifuge> {

    public static Map<String,IBakedModel> cache= new HashMap< String,IBakedModel>();
    public float timer = 0;


    @Override
    public void render(TileCentrifuge te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {


        cache.putIfAbsent("model",ModelsCache.INSTANCE.getBakedModel(new ResourceLocation(MyMod.MODID, "block/centrifuge_armrotate")));



        IBakedModel model;
        model = cache.get("model");
        if(model == null){
            model = cache.get("model");
        }



        RenderHelper.disableStandardItemLighting();

        GlStateManager.pushMatrix();

        Tessellator tessellator = Tessellator.getInstance();


        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        timer = timer + 3;



        GlStateManager.translate(x + 8/16d,y,z + 8/16D);
        GlStateManager.scale(1, 1, 1);

          GlStateManager.rotate((float)te.dealwithpartialticks(partialTicks), 0F, 1F, 0F);

     //   GlStateManager.rotate( te.cosmeticRPM + partialTicks, 0F, 1F, 0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

   //     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin( GL11.GL_QUADS, DefaultVertexFormats.BLOCK );

      //  buffer.setTranslation( -te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ() );
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        buffer.setTranslation( -te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ() );

        dispatcher.getBlockModelRenderer().renderModel(te.getWorld(), model, te.getBlockState(), te.getPos(), buffer, false);
        buffer.setTranslation( 0, 0, 0 );
        tessellator.draw();
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();


        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.defaultTexUnit, 240, 240);





        }


        public void renderQuads(List<BakedQuad> quads, BufferBuilder builder){


        }


    public float getInterpol(float a, float b, float f){
        if(f < 7){
            return a+f*(b-a);
        }else{
            return a+6*(b-a);
        }

    }




    }

