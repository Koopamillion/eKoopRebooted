package koopamillion.mymod.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.ModItems;
import koopamillion.mymod.ModLiquids;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.acidbath.TileAcidBath;
import koopamillion.mymod.furnace.BlockFurnace;
import koopamillion.mymod.furnace.TileFurnace;
import koopamillion.mymod.generators.BlockGenerator;
import koopamillion.mymod.generators.TileGenerator;
import koopamillion.mymod.machineitems.ItemCircuit;
import koopamillion.mymod.machineitems.ItemEnderIngot;
import koopamillion.mymod.machineitems.ItemSolderIngot;
import koopamillion.mymod.network.Messages;
import koopamillion.mymod.plushy.BlockChickenPlushy;
import koopamillion.mymod.plushy.TileChickenPlushy;
import koopamillion.mymod.saturator.BlockSaturator;
import koopamillion.mymod.saturator.TileSaturator;
import koopamillion.mymod.simpleblocks.BlockMachineFrame;
import koopamillion.mymod.solder.BlockEnderSolder;
import koopamillion.mymod.solder.BlockHCL;
import koopamillion.mymod.solder.BlockSolder;
import koopamillion.mymod.soldertable.BlockSolderPart;
import koopamillion.mymod.soldertable.BlockSolderTable;
import koopamillion.mymod.soldertable.TileSolderTable;
import koopamillion.mymod.acidbath.BlockAcidBath;
import koopamillion.mymod.tools.Oredict;
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


        Messages.registerMessages("mymod");
        ModLiquids.init();
    }

    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(MyMod.instance, new GuiHandler());


    }

    public void postInit(FMLPostInitializationEvent e) {
        Oredict.init();
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
        event.getRegistry().register(new BlockEnderSolder());
        event.getRegistry().register(new BlockHCL());
        event.getRegistry().register(new BlockAcidBath());
        event.getRegistry().register(new BlockGenerator());
        event.getRegistry().register(new BlockSaturator());
        GameRegistry.registerTileEntity(TileFurnace.class, MyMod.MODID + "_furnace");
        GameRegistry.registerTileEntity(TileGenerator.class, MyMod.MODID + "_generator");
        GameRegistry.registerTileEntity(TileChickenPlushy.class, MyMod.MODID + "_chicken");
        GameRegistry.registerTileEntity(TileSolderTable.class, MyMod.MODID+"_solder");
        GameRegistry.registerTileEntity(TileAcidBath.class, MyMod.MODID+"_acidbath");
        GameRegistry.registerTileEntity(TileSaturator.class, MyMod.MODID+"_saturator");

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock (ModBlocks.blockFurnace).setRegistryName(BlockFurnace.afurnace));
        event.getRegistry().register(new ItemCircuit());
        event.getRegistry().registerAll(new Item().setTranslationKey("mymod.siliconraw").setRegistryName("siliconraw").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("mymod.siliconpure").setRegistryName("siliconpure").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("mymod.puresand").setRegistryName("puresand").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("mymod.salt").setRegistryName("salt").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("mymod.photon").setRegistryName("photon").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("mymod.energy").setRegistryName("energy").setCreativeTab(MyMod.tabEKoop).setMaxStackSize(1),
                new Item().setTranslationKey("mymod.ender").setRegistryName("ender").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("mymod.trace").setRegistryName("trace").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("mymod.mixed").setRegistryName("mixed").setCreativeTab(MyMod.tabEKoop));
        event.getRegistry().register(new ItemSolderIngot());
        event.getRegistry().register(new ItemEnderIngot());
        event.getRegistry().register(new ItemBlock (ModBlocks.blockSolder).setRegistryName(BlockSolderTable.solder));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockChicken).setRegistryName(BlockChickenPlushy.chicken));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockMachineFrame).setRegistryName(BlockMachineFrame.machineframe));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockGenerator).setRegistryName(BlockGenerator.generator));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockSaturator).setRegistryName(BlockSaturator.saturator));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockSolderPart).setRegistryName(BlockSolderPart.solderpart));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockAcidbath).setRegistryName(BlockAcidBath.acidbath));

    }

  /*  @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event){

    }*/
    public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
        throw new IllegalStateException("This should only be called from client side");
    }






    public EntityPlayer getClientPlayer() {
        throw new IllegalStateException("This should only be called from client side");
    }
}

