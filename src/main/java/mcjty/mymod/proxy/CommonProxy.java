package mcjty.mymod.proxy;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.MyMod;
import mcjty.mymod.charger.BlockDroneCharger;
import mcjty.mymod.furnace.BlockFurnace;
import mcjty.mymod.furnace.TileFurnace;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(MyMod.instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockFurnace());
        event.getRegistry().register(new BlockDroneCharger());
        GameRegistry.registerTileEntity(TileFurnace.class, MyMod.MODID + "_furnace");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock (ModBlocks.blockFurnace).setRegistryName(BlockFurnace.afurnace));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockCharger).setRegistryName(BlockDroneCharger.aCharger));
    }
}

