package mcjty.mymod;

import mcjty.mymod.charger.BlockDroneCharger;
import mcjty.mymod.furnace.BlockFurnace;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("mymod:furnace")
    public static BlockFurnace blockFurnace;
   @GameRegistry.ObjectHolder("mymod:charger")
    public static BlockDroneCharger blockCharger;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockFurnace.initModel();
        blockCharger.initModel();


    }




}
