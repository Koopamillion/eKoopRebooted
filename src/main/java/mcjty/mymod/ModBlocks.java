package mcjty.mymod;

import mcjty.mymod.furnace.BlockFurnace;
import mcjty.mymod.plushy.BlockChickenPlushy;
import mcjty.mymod.simpleblocks.BlockMachineFrame;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("mymod:furnace")
    public static BlockFurnace blockFurnace;


   @GameRegistry.ObjectHolder("mymod:chicken")
    public static BlockChickenPlushy blockChicken;

    @GameRegistry.ObjectHolder("mymod:machineframe")
    public static BlockMachineFrame blockMachineFrame;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockFurnace.initModel();
        blockChicken.initModel();
        blockMachineFrame.initModel();


    }




}
