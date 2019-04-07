package koopamillion.mymod.tools;

import koopamillion.mymod.MyMod;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.HashSet;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MyMod.MODID)
/***
 * https://github.com/Cadiboo/WIPTech/tree/fb5883e9d76ef0361ec1ebbcb9c508611dd2ef6b/src/main/java/cadiboo/wiptech
 * Huge thanks to Cadiboo for helping me understand models
 */
public class ClientEventSubscriber {

    @SubscribeEvent
    public static void onModelBakeEvent(final ModelBakeEvent event) {
        final IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();

        injectModels(registry);
        MyMod.logger.info("Injected models");

    }


    private static void injectModels(final IRegistry<ModelResourceLocation, IBakedModel> registry) {
        final HashSet<ResourceLocation> modelLocations = new HashSet<>();

        modelLocations.add(new ResourceLocation(MyMod.MODID, "block/centrifuge"));

        for (final ResourceLocation modelLocation : modelLocations) {
            try {
                // modified from code made by Draco18s
                final ModelResourceLocation location = new ModelResourceLocation(modelLocation.toString());

                final IBakedModel bakedModel = ModelsCache.INSTANCE.getBakedModel(modelLocation);

                registry.putObject(location, bakedModel);
                MyMod.logger.debug("Sucessfully injected " + modelLocation.toString() + " into Model Registry");
            } catch (final Exception e) {
                MyMod.logger.error("Error injecting model " + modelLocation.toString() + " into Model Registry");
            }

       }
    }
}
