package koopamillion.mymod;

import koopamillion.mymod.machineitems.ItemCircuit;
import koopamillion.mymod.machineitems.ItemMobHolder;
import koopamillion.mymod.machineitems.ItemSolderIngot;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    @GameRegistry.ObjectHolder("ekooprebooted:circuit")
    public static ItemCircuit itemCircuit;
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




    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemCircuit.initModel();
        itemSolder.initModel();
        mobholder.initModel();
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
    }
}
