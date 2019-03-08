package koopamillion.mymod;

import koopamillion.mymod.machineitems.ItemCircuit;
import koopamillion.mymod.machineitems.ItemSolderIngot;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    @GameRegistry.ObjectHolder("mymod:circuit")
    public static ItemCircuit itemCircuit;
    @GameRegistry.ObjectHolder("mymod:solderingot")
    public static ItemSolderIngot itemSolder;

    @GameRegistry.ObjectHolder("mymod:siliconraw")
    public static Item siliconraw;

    @GameRegistry.ObjectHolder("mymod:siliconpure")
    public static Item siliconpure;
    @GameRegistry.ObjectHolder("mymod:puresand")
    public static Item puresand;
    @GameRegistry.ObjectHolder("mymod:salt")
    public static Item salt;
    @GameRegistry.ObjectHolder("mymod:photon")
    public static Item photon;




    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemCircuit.initModel();
        itemSolder.initModel();
        ModelLoader.setCustomModelResourceLocation(siliconraw, 0, new ModelResourceLocation(siliconraw.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(siliconpure, 0, new ModelResourceLocation(siliconpure.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(puresand, 0, new ModelResourceLocation(puresand.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(salt, 0, new ModelResourceLocation(salt.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(photon, 0, new ModelResourceLocation(photon.getRegistryName(), "inventory"));
    }
}
