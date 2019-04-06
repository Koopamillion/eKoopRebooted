package koopamillion.mymod;

import koopamillion.mymod.machineitems.*;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    @GameRegistry.ObjectHolder("ekooprebooted:circuit")
    public static ItemCircuit itemCircuit;
    @GameRegistry.ObjectHolder("ekooprebooted:advancedcircuit")
    public static Item advancedcircuit;
    @GameRegistry.ObjectHolder("ekooprebooted:solderingot")
    public static ItemSolderIngot itemSolder;

    @GameRegistry.ObjectHolder("ekooprebooted:siliconraw")
    public static Item siliconraw;

    @GameRegistry.ObjectHolder("ekooprebooted:siliconpure")
    public static Item siliconpure;
    @GameRegistry.ObjectHolder("ekooprebooted:puresand")
    public static Item puresand;
    @GameRegistry.ObjectHolder("ekooprebooted:salt")
    public static Item salt;
    @GameRegistry.ObjectHolder("ekooprebooted:photon")
    public static Item photon;
    @GameRegistry.ObjectHolder("ekooprebooted:energy")
    public static Item energy;
    @GameRegistry.ObjectHolder("ekooprebooted:ender")
    public static Item ender;
    @GameRegistry.ObjectHolder("ekooprebooted:enderingot")
    public static Item enderingot;
    @GameRegistry.ObjectHolder("ekooprebooted:trace")
    public static Item trace;
    @GameRegistry.ObjectHolder("ekooprebooted:mixed")
    public static Item mixed;

    @GameRegistry.ObjectHolder("ekooprebooted:mobholder")
    public static ItemMobHolder mobholder;
    @GameRegistry.ObjectHolder("ekooprebooted:emeraldnugget")
    public static Item emeraldnugget;
    @GameRegistry.ObjectHolder("ekooprebooted:dragonnugget")
    public static Item dragonnugget;
    @GameRegistry.ObjectHolder("ekooprebooted:nethernugget")
    public static Item nethernugget;
    @GameRegistry.ObjectHolder("ekooprebooted:lifecore")
    public static Item lifecore;
    @GameRegistry.ObjectHolder("ekooprebooted:dragonheartnugget")
    public static Item dragonheartnugget;
    @GameRegistry.ObjectHolder("ekooprebooted:withernugget")
    public static Item withernugget;
    @GameRegistry.ObjectHolder("ekooprebooted:mobram")
    public static ItemMobRAM mobram;

    @GameRegistry.ObjectHolder("ekooprebooted:tinysilver")
    public static Item tinysilver;
    @GameRegistry.ObjectHolder("ekooprebooted:tinycopper")
    public static Item tinycopper;
    @GameRegistry.ObjectHolder("ekooprebooted:tinyantinomy")
    public static Item tinyantinomy;
    @GameRegistry.ObjectHolder("ekooprebooted:tinytin")
    public static Item tinytin;
    @GameRegistry.ObjectHolder("ekooprebooted:tinylead")
    public static Item tinylead;
    @GameRegistry.ObjectHolder("ekooprebooted:tinyaluminium")
    public static Item tinyaluminium;
    @GameRegistry.ObjectHolder("ekooprebooted:tinyiron")
    public static Item tinyiron;
    @GameRegistry.ObjectHolder("ekooprebooted:tinyshinys")
    public static Item tinyshinys;
    @GameRegistry.ObjectHolder("ekooprebooted:tinystone")
    public static Item tinystone;


    @GameRegistry.ObjectHolder("ekooprebooted:dustsilver")
    public static Item dustsilver;
    @GameRegistry.ObjectHolder("ekooprebooted:dustcopper")
    public static Item dustcopper;
    @GameRegistry.ObjectHolder("ekooprebooted:dustantinomy")
    public static Item dustantinomy;
    @GameRegistry.ObjectHolder("ekooprebooted:dusttin")
    public static Item dusttin;
    @GameRegistry.ObjectHolder("ekooprebooted:dustlead")
    public static Item dustlead;
    @GameRegistry.ObjectHolder("ekooprebooted:dustaluminium")
    public static Item dustaluminium;
    @GameRegistry.ObjectHolder("ekooprebooted:dustiron")
    public static Item dustiron;
    @GameRegistry.ObjectHolder("ekooprebooted:dustshinys")
    public static Item dustshinys;
    @GameRegistry.ObjectHolder("ekooprebooted:duststone")
    public static Item duststone;

    @GameRegistry.ObjectHolder("ekooprebooted:ingotsilver")
    public static Item ingotsilver;
    @GameRegistry.ObjectHolder("ekooprebooted:ingotcopper")
    public static Item ingotcopper;
    @GameRegistry.ObjectHolder("ekooprebooted:ingotantinomy")
    public static Item ingotantinomy;
    @GameRegistry.ObjectHolder("ekooprebooted:ingottin")
    public static Item ingottin;
    @GameRegistry.ObjectHolder("ekooprebooted:ingotlead")
    public static Item ingotlead;
    @GameRegistry.ObjectHolder("ekooprebooted:ingotaluminium")
    public static Item ingotaluminium;

    @GameRegistry.ObjectHolder("ekooprebooted:silversolderingot")
    public static ItemSilverSolderIngot silversolderingot;
    @GameRegistry.ObjectHolder("ekooprebooted:dustcharcoal")
    public static Item dustcharcoal;
    @GameRegistry.ObjectHolder("ekooprebooted:dustsulfur")
    public static Item dustsulfur;
    @GameRegistry.ObjectHolder("ekooprebooted:dustsaltpeter")
    public static Item dustsaltpeter;
    @GameRegistry.ObjectHolder("ekooprebooted:dirty")
    public static Item dirty;
    @GameRegistry.ObjectHolder("ekooprebooted:clean")
    public static Item clean;








    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemCircuit.initModel();
        itemSolder.initModel();
        mobholder.initModel();
        mobram.initModel();
        silversolderingot.initModel();
        ModelLoader.setCustomModelResourceLocation(siliconraw, 0, new ModelResourceLocation(siliconraw.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(siliconpure, 0, new ModelResourceLocation(siliconpure.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(puresand, 0, new ModelResourceLocation(puresand.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(salt, 0, new ModelResourceLocation(salt.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(photon, 0, new ModelResourceLocation(photon.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(energy, 0, new ModelResourceLocation(energy.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ender, 0, new ModelResourceLocation(ender.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(enderingot, 0, new ModelResourceLocation(enderingot.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(trace, 0, new ModelResourceLocation(trace.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(mixed, 0, new ModelResourceLocation(mixed.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(emeraldnugget, 0, new ModelResourceLocation(emeraldnugget.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dragonnugget, 0, new ModelResourceLocation(dragonnugget.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(nethernugget, 0, new ModelResourceLocation(nethernugget.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(lifecore, 0, new ModelResourceLocation(lifecore.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dragonheartnugget, 0, new ModelResourceLocation(dragonheartnugget.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(withernugget, 0, new ModelResourceLocation(withernugget.getRegistryName(), "inventory"));

        ModelLoader.setCustomModelResourceLocation(tinyaluminium, 0, new ModelResourceLocation(tinyaluminium.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(tinyantinomy, 0, new ModelResourceLocation(tinyantinomy.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(tinycopper, 0, new ModelResourceLocation(tinycopper.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(tinyiron, 0, new ModelResourceLocation(tinyiron.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(tinylead, 0, new ModelResourceLocation(tinylead.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(tinyshinys, 0, new ModelResourceLocation(tinyshinys.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(tinysilver, 0, new ModelResourceLocation(tinysilver.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(tinystone, 0, new ModelResourceLocation(tinystone.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(tinytin, 0, new ModelResourceLocation(tinytin.getRegistryName(), "inventory"));

        ModelLoader.setCustomModelResourceLocation(dustaluminium, 0, new ModelResourceLocation(dustaluminium.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dustantinomy, 0, new ModelResourceLocation(dustantinomy.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dustcopper, 0, new ModelResourceLocation(dustcopper.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dustiron, 0, new ModelResourceLocation(dustiron.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dustlead, 0, new ModelResourceLocation(dustlead.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dustshinys, 0, new ModelResourceLocation(dustshinys.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dustsilver, 0, new ModelResourceLocation(dustsilver.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(duststone, 0, new ModelResourceLocation(duststone.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dusttin, 0, new ModelResourceLocation(dusttin.getRegistryName(), "inventory"));

        ModelLoader.setCustomModelResourceLocation(ingotaluminium, 0, new ModelResourceLocation(ingotaluminium.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ingotantinomy, 0, new ModelResourceLocation(ingotantinomy.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ingotcopper, 0, new ModelResourceLocation(ingotcopper.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ingotlead, 0, new ModelResourceLocation(ingotlead.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ingotsilver, 0, new ModelResourceLocation(ingotsilver.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ingottin, 0, new ModelResourceLocation(ingottin.getRegistryName(), "inventory"));

        ModelLoader.setCustomModelResourceLocation(advancedcircuit, 0, new ModelResourceLocation(advancedcircuit.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dustcharcoal, 0, new ModelResourceLocation(dustcharcoal.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dustsulfur, 0, new ModelResourceLocation(dustsulfur.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dustsaltpeter, 0, new ModelResourceLocation(dustsaltpeter.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(dirty, 0, new ModelResourceLocation(dirty.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(clean, 0, new ModelResourceLocation(clean.getRegistryName(), "inventory"));
    }
}
