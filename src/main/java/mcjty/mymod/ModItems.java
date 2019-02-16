package mcjty.mymod;

import mcjty.mymod.machineitems.ItemCircuit;
import mcjty.mymod.machineitems.ItemSolderIngot;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    @GameRegistry.ObjectHolder("mymod:circuit")
    public static ItemCircuit itemCircuit;
    @GameRegistry.ObjectHolder("mymod:solderingot")
    public static ItemSolderIngot itemSolder;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemCircuit.initModel();
        itemSolder.initModel();
    }
}
