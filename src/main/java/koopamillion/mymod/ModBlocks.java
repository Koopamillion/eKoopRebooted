package koopamillion.mymod;

import koopamillion.mymod.generators.BlockGenerator;
import koopamillion.mymod.plushy.BlockChickenPlushy;
import koopamillion.mymod.saturator.BlockSaturator;
import koopamillion.mymod.simpleblocks.BlockMachineFrame;
import koopamillion.mymod.solder.BlockEnderSolder;
import koopamillion.mymod.solder.BlockHCL;
import koopamillion.mymod.soldertable.BlockSolderPart;
import koopamillion.mymod.soldertable.BlockSolderTable;
import koopamillion.mymod.acidbath.BlockAcidBath;
import koopamillion.mymod.furnace.BlockFurnace;
import koopamillion.mymod.solder.BlockSolder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("mymod:furnace")
    public static BlockFurnace blockFurnace;

    @GameRegistry.ObjectHolder("mymod:solder")
    public static BlockSolderTable blockSolder;


   @GameRegistry.ObjectHolder("mymod:chicken")
    public static BlockChickenPlushy blockChicken;

    @GameRegistry.ObjectHolder("mymod:machineframe")
    public static BlockMachineFrame blockMachineFrame;

    @GameRegistry.ObjectHolder("mymod:solderpart")
    public static BlockSolderPart blockSolderPart;
    @GameRegistry.ObjectHolder("mymod:saturator")
    public static BlockSaturator blockSaturator;
    @GameRegistry.ObjectHolder("mymod:solderfluid")
    public static BlockSolder blockSolderFluid;
    @GameRegistry.ObjectHolder("mymod:enderfluid")
    public static BlockEnderSolder blockEnderFluid;
    @GameRegistry.ObjectHolder("mymod:hcl")
    public static BlockHCL blockHCL;
    @GameRegistry.ObjectHolder("mymod:generator")
    public static BlockGenerator blockGenerator;

    @GameRegistry.ObjectHolder("mymod:acidbath")
    public static BlockAcidBath blockAcidbath;


    @SideOnly(Side.CLIENT)
    public static void initModels() {
        blockFurnace.initModel();
        blockChicken.initModel();
        blockMachineFrame.initModel();
        blockSolder.initModel();
        blockSolderPart.initModel();
        blockSolderFluid.initModel();
        blockAcidbath.initModel();
        blockHCL.initModel();
        blockSaturator.initModel();
        blockEnderFluid.initModel();

        blockGenerator.initModel();

    }




}
