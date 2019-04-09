package koopamillion.mymod.ballmill;

import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.centrifuge.TileCentrifuge;
import koopamillion.mymod.soldertable.BlockSolderTable;
import koopamillion.mymod.tools.ModelsCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BallimllTESR extends TileEntitySpecialRenderer<TileBallmill> {

    public static Map<String,IBakedModel> cache= new HashMap< String,IBakedModel>();
    public float timer = 0;


    @Override
    public void render(TileBallmill te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {


        cache.putIfAbsent("model",ModelsCache.INSTANCE.getBakedModel(new ResourceLocation(MyMod.MODID, "block/ballmill_rotate")));



        IBakedModel model;
        model = cache.get("model");
        if(model == null){
            model = cache.get("model");
        }





        GlStateManager.pushMatrix();

        Tessellator tessellator = Tessellator.getInstance();

        net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();



        bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        timer = timer + 3;



      //  GlStateManager.translate(x + 8/16d,y+9/16d,z + 7/16D);
        GlStateManager.scale(1, 1, 1);

        if(te.getBlockState().getBlock() == ModBlocks.blockBallmill) {
            if (te.getBlockState().getValue(BlockBallmill.FACING).getHorizontalIndex() == EnumFacing.NORTH.getHorizontalIndex()) {
                GlStateManager.translate(x + 7/16d,y+9/16d,z + 8/16D);
                GlStateManager.rotate(90, 0,1,0);

            }
            if (te.getBlockState().getValue(BlockBallmill.FACING).getHorizontalIndex() == EnumFacing.SOUTH.getHorizontalIndex()) {
                GlStateManager.translate(x + 7/16d,y+9/16d,z + 8/16D);
                    GlStateManager.rotate(90, 0,1,0);

            }
            if (te.getBlockState().getValue(BlockBallmill.FACING).getHorizontalIndex() == EnumFacing.WEST.getHorizontalIndex()) {
                GlStateManager.translate(x + 8/16d,y+9/16d,z + 7/16D);
            }
            if (te.getBlockState().getValue(BlockBallmill.FACING).getHorizontalIndex() == EnumFacing.EAST.getHorizontalIndex()) {
                GlStateManager.translate(x + 8/16d,y+9/16d,z + 7/16D);
            }
        }

          GlStateManager.rotate((float)te.dealwithpartialticks(partialTicks), 0F, 0f, 1F);



     //   GlStateManager.rotate( te.cosmeticRPM + partialTicks, 0F, 1F, 0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

   //     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);


        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin( GL11.GL_QUADS, DefaultVertexFormats.BLOCK );

      //  buffer.setTranslation( -te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ() );
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
       // Minecraft.getMinecraft().getRenderItem().renderModel(model, 1);

       // RenderItem r = Minecraft.getMinecraft().getRenderItem();
        //r.renderModel(model, 0);
        buffer.setTranslation( -te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ() );



        dispatcher.getBlockModelRenderer().renderModel(te.getWorld(), model, te.getBlockState(), te.getPos(), buffer, false);

        buffer.setTranslation( 0, 0, 0 );
        tessellator.draw();
        GlStateManager.popMatrix();



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

