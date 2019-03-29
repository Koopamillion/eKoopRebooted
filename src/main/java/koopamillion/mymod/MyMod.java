package koopamillion.mymod;


import koopamillion.mymod.sound.SoundFurnace;
import koopamillion.mymod.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = MyMod.MODID, name = MyMod.MODNAME, version = MyMod.MODVERSION, dependencies = "required-after:forge@[14.23.5.2768,)", useMetadata = true)
public class MyMod {

    public static final String MODID = "ekooprebooted";
    public static final String MODNAME = "eKoop Rebooted";
    public static final String MODVERSION= "0.0.1";
    public static SimpleNetworkWrapper network;
    @SidedProxy(clientSide = "koopamillion.mymod.proxy.ClientProxy", serverSide = "koopamillion.mymod.proxy.ServerProxy")
    public static CommonProxy proxy;
    //Creative tab for eKoop
    public static CreativeTabs tabEKoop = new CreativeTabs("eKoop") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.blockFurnace);
        }
    };

    public MyMod(){
        FluidRegistry.enableUniversalBucket();
    }

    @Mod.Instance
    public static MyMod instance;

    public static Logger logger = LogManager.getLogger(MODNAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MyMod.logger.info("Pre Init Time!");
        logger = event.getModLog();
        proxy.preInit(event);
        SoundFurnace.preInit();

    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        ModRecipes.init();
        proxy.init(e);
        MyMod.logger.info("Initializing");

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
        MyMod.logger.info("Finishing up!");
    }
}