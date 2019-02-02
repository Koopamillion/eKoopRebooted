package mcjty.mymod;

import mcjty.mymod.machineitems.ItemCircuit;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {
    @GameRegistry.ObjectHolder("mymod:circuit")
    public static ItemCircuit itemCircuit;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemCircuit.initModel();
    }
}
