package koopamillion.mymod.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.ModItems;
import koopamillion.mymod.MyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public static SimpleNetworkWrapper network;
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
        OBJLoader.INSTANCE.addDomain(MyMod.MODID);
    //    ClientRegistry.bindTileEntitySpecialRenderer(TileSolderTable.class, Sol);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

        ModBlocks.initModels();
        ModItems.initModels();
    }



    @Override
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
    }

    @Override
    public EntityPlayer getClientPlayer() {
        return Minecraft.getMinecraft().player;
    }

}