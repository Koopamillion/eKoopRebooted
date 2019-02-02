package mcjty.mymod.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import mcjty.mymod.ModBlocks;
import mcjty.mymod.MyMod;
import mcjty.mymod.furnace.BlockFurnace;
import mcjty.mymod.furnace.TileFurnace;
import mcjty.mymod.network.Messages;
import mcjty.mymod.plushy.BlockChickenPlushy;
import mcjty.mymod.plushy.TileChickenPlushy;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
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


        Messages.registerMessages("mymod");

    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(MyMod.instance, new GuiHandler());

    }

    public void postInit(FMLPostInitializationEvent e) {
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockFurnace());
        event.getRegistry().register(new BlockChickenPlushy());
        GameRegistry.registerTileEntity(TileFurnace.class, MyMod.MODID + "_furnace");
        GameRegistry.registerTileEntity(TileChickenPlushy.class, MyMod.MODID + "_chicken");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock (ModBlocks.blockFurnace).setRegistryName(BlockFurnace.afurnace));

        event.getRegistry().register(new ItemBlock (ModBlocks.blockChicken).setRegistryName(BlockChickenPlushy.chicken));
    }



    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }






    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }
}

