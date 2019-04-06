package koopamillion.mymod.proxy;

import com.google.common.util.concurrent.ListenableFuture;
import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.ModItems;
import koopamillion.mymod.ModLiquids;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.acidbath.TileAcidBath;
import koopamillion.mymod.centrifuge.BlockCentrifuge;
import koopamillion.mymod.centrifuge.TileCentrifuge;
import koopamillion.mymod.dna.BlockDNAExtractor;
import koopamillion.mymod.dna.TileDNAExtractor;
import koopamillion.mymod.furnace.BlockFurnace;
import koopamillion.mymod.furnace.TileFurnace;
import koopamillion.mymod.generators.BlockGenerator;
import koopamillion.mymod.generators.TileGenerator;
import koopamillion.mymod.machineitems.*;
import koopamillion.mymod.network.Messages;
import koopamillion.mymod.plugins.PluginDraconicEvolution;
import koopamillion.mymod.plugins.PluginInit;
import koopamillion.mymod.plugins.PluginMysticalAgriculture;
import koopamillion.mymod.plugins.PluginThermalExpansion;
import koopamillion.mymod.plushy.BlockChickenPlushy;
import koopamillion.mymod.plushy.TileChickenPlushy;
import koopamillion.mymod.saturator.BlockSaturator;
import koopamillion.mymod.saturator.TileSaturator;
import koopamillion.mymod.simpleblocks.*;
import koopamillion.mymod.solder.BlockEnderSolder;
import koopamillion.mymod.solder.BlockHCL;
import koopamillion.mymod.solder.BlockSilverSolder;
import koopamillion.mymod.solder.BlockSolder;
import koopamillion.mymod.soldertable.BlockSolderPart;
import koopamillion.mymod.soldertable.BlockSolderTable;
import koopamillion.mymod.soldertable.TileSolderTable;
import koopamillion.mymod.acidbath.BlockAcidBath;
import koopamillion.mymod.tools.Oredict;
import koopamillion.mymod.tools.PlushyStuff;
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
        PlushyStuff.initDefaultLoot();
        PluginInit.init();
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
        event.getRegistry().register(new BlockSilverSolder());
        event.getRegistry().register(new BlockAcidBath());
        event.getRegistry().register(new BlockGenerator());
        event.getRegistry().register(new BlockSaturator());
        event.getRegistry().register(new BlockDNAExtractor());
        event.getRegistry().register(new BlockCentrifuge());
        event.getRegistry().register(new BlockMotor());
        event.getRegistry().register(new BlockAugmentedStone());
        event.getRegistry().register(new BlockAdvancedCasing());
        event.getRegistry().register(new BlockAdvancedMachineFrame());
        GameRegistry.registerTileEntity(TileFurnace.class, MyMod.MODID + "_furnace");
        GameRegistry.registerTileEntity(TileGenerator.class, MyMod.MODID + "_generator");
        GameRegistry.registerTileEntity(TileChickenPlushy.class, MyMod.MODID + "_chicken");
        GameRegistry.registerTileEntity(TileSolderTable.class, MyMod.MODID+"_solder");
        GameRegistry.registerTileEntity(TileAcidBath.class, MyMod.MODID+"_acidbath");
        GameRegistry.registerTileEntity(TileSaturator.class, MyMod.MODID+"_saturator");
        GameRegistry.registerTileEntity(TileDNAExtractor.class, MyMod.MODID+"_dnaextractor");
        GameRegistry.registerTileEntity(TileCentrifuge.class, MyMod.MODID+"_centrifuge");
        GameRegistry.registerTileEntity(TileMotor.class, MyMod.MODID+"_motor");

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock (ModBlocks.blockFurnace).setRegistryName(BlockFurnace.afurnace));
        event.getRegistry().register(new ItemCircuit());
        event.getRegistry().register(new ItemMobHolder());
        event.getRegistry().register(new ItemMobRAM());
        event.getRegistry().register(new ItemSilverSolderIngot());
        event.getRegistry().registerAll(new Item().setTranslationKey("ekooprebooted.siliconraw").setRegistryName("siliconraw").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.siliconpure").setRegistryName("siliconpure").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.puresand").setRegistryName("puresand").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.salt").setRegistryName("salt").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.photon").setRegistryName("photon").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.energy").setRegistryName("energy").setCreativeTab(MyMod.tabEKoop).setMaxStackSize(1),
                new Item().setTranslationKey("ekooprebooted.ender").setRegistryName("ender").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.trace").setRegistryName("trace").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.mixed").setRegistryName("mixed").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.emeraldnugget").setRegistryName("emeraldnugget").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dragonnugget").setRegistryName("dragonnugget").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.nethernugget").setRegistryName("nethernugget").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.lifecore").setRegistryName("lifecore").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dragonheartnugget").setRegistryName("dragonheartnugget").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.withernugget").setRegistryName("withernugget").setCreativeTab(MyMod.tabEKoop),

                new Item().setTranslationKey("ekooprebooted.tinyaluminium").setRegistryName("tinyaluminium").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.tinyantinomy").setRegistryName("tinyantinomy").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.tinycopper").setRegistryName("tinycopper").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.tinysilver").setRegistryName("tinysilver").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.tinytin").setRegistryName("tinytin").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.tinyiron").setRegistryName("tinyiron").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.tinyshinys").setRegistryName("tinyshinys").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.tinystone").setRegistryName("tinystone").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.tinylead").setRegistryName("tinylead").setCreativeTab(MyMod.tabEKoop),

                new Item().setTranslationKey("ekooprebooted.dustaluminium").setRegistryName("dustaluminium").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dustantinomy").setRegistryName("dustantinomy").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dustcopper").setRegistryName("dustcopper").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dustsilver").setRegistryName("dustsilver").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dusttin").setRegistryName("dusttin").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dustiron").setRegistryName("dustiron").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dustshinys").setRegistryName("dustshinys").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.duststone").setRegistryName("duststone").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dustlead").setRegistryName("dustlead").setCreativeTab(MyMod.tabEKoop),

                new Item().setTranslationKey("ekooprebooted.ingotaluminium").setRegistryName("ingotaluminium").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.ingotlead").setRegistryName("ingotlead").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.ingottin").setRegistryName("ingottin").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.ingotcopper").setRegistryName("ingotcopper").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.ingotantinomy").setRegistryName("ingotantinomy").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.ingotsilver").setRegistryName("ingotsilver").setCreativeTab(MyMod.tabEKoop),

                new Item().setTranslationKey("ekooprebooted.advancedcircuit").setRegistryName("advancedcircuit").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dustsulfur").setRegistryName("dustsulfur").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dustcharcoal").setRegistryName("dustcharcoal").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dustsaltpeter").setRegistryName("dustsaltpeter").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.dirty").setRegistryName("dirty").setCreativeTab(MyMod.tabEKoop),
                new Item().setTranslationKey("ekooprebooted.clean").setRegistryName("clean").setCreativeTab(MyMod.tabEKoop)



        );
        event.getRegistry().register(new ItemSolderIngot());
        event.getRegistry().register(new ItemEnderIngot());
        event.getRegistry().register(new ItemBlock (ModBlocks.blockSolder).setRegistryName(BlockSolderTable.solder));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockChicken).setRegistryName(BlockChickenPlushy.chicken));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockMachineFrame).setRegistryName(BlockMachineFrame.machineframe));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockGenerator).setRegistryName(BlockGenerator.generator));
        event.getRegistry().register(new ItemBlock (ModBlocks.blockSaturator).setRegistryName(BlockSaturator.saturator));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockSolderPart).setRegistryName(BlockSolderPart.solderpart));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockAcidbath).setRegistryName(BlockAcidBath.acidbath));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockDNAExtractor).setRegistryName(BlockDNAExtractor.dnaextractor));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockAugmentedStone).setRegistryName(BlockAugmentedStone.augstone));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockCentrifuge).setRegistryName(BlockCentrifuge.centrifuge));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockMotor).setRegistryName(BlockMotor.motor));
        event.getRegistry().register(new ItemBlock(ModBlocks.advancedCasing).setRegistryName(BlockAdvancedCasing.advancedcasing));
        event.getRegistry().register(new ItemBlock(ModBlocks.advancedMachineFrame).setRegistryName(BlockAdvancedMachineFrame.advancedmachineframe));
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

