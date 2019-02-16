package mcjty.mymod.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import mcjty.mymod.ModBlocks;
import mcjty.mymod.ModItems;
import mcjty.mymod.ModLiquids;
import mcjty.mymod.MyMod;
import mcjty.mymod.furnace.BlockFurnace;
import mcjty.mymod.furnace.TileFurnace;
import mcjty.mymod.machineitems.ItemCircuit;
import mcjty.mymod.machineitems.ItemSolderIngot;
import mcjty.mymod.network.Messages;
import mcjty.mymod.plushy.BlockChickenPlushy;
import mcjty.mymod.plushy.TileChickenPlushy;
import mcjty.mymod.simpleblocks.BlockMachineFrame;
import mcjty.mymod.solder.BlockSolder;
import mcjty.mymod.soldertable.BlockSolderPart;
import mcjty.mymod.soldertable.BlockSolderTable;
import mcjty.mymod.soldertable.TileSolderTable;
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
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class CommonProxy {
    public void preInit(FMLPreInitializationEvent e) {

        OreDictionary.registerOre("circuitBasic", ModItems.itemCircuit);
        Messages.registerMessages("mymod");
        ModLiquids.init();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(MyMod.instance, new GuiHandler());


    }

    public void postInit(FMLPostInitializationEvent e) {
    }
  /*  @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> event){
        SoundFurnace.init(event);
    }*/

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockFurnace());
        event.getRegistry().register(new BlockChickenPlushy());
        event.getRegistry().register(new BlockMachineFrame());
        event.getRegistry().register(new BlockSolderTable());
        event.getRegistry().register(new BlockSolderPart());
        event.getRegistry().register(new BlockSolder());
        GameRegistry.registerTileEntity(TileFurnace.class, MyMod.MODID + "_furnace");
        GameRegistry.registerTileEntity(TileChickenPlushy.class, MyMod.MODID + "_chicken");
        GameRegistry.registerTileEntity(TileSolderTable.class, MyMod.MODID+"_solder");

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock (ModBlocks.blockFurnace).setRegistryName(BlockFurnace.afurnace));
        event.getRegistry().register(new ItemCircuit());
        event.getRegistry().register(new ItemSolderIngot());
        event.getRegistry().register(new ItemBlock (ModBlocks.blockSolder).setRegistryName(BlockSolderTable.solder));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockChicken).setRegistryName(BlockChickenPlushy.chicken));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockMachineFrame).setRegistryName(BlockMachineFrame.machineframe));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockSolderPart).setRegistryName(BlockSolderPart.solderpart));
    }



    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }






    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }
}

