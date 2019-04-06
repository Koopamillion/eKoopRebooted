package koopamillion.mymod;

import koopamillion.mymod.centrifuge.BlockCentrifuge;
import koopamillion.mymod.dna.BlockDNAExtractor;
import koopamillion.mymod.generators.BlockGenerator;
import koopamillion.mymod.plushy.BlockChickenPlushy;
import koopamillion.mymod.saturator.BlockSaturator;
import koopamillion.mymod.simpleblocks.*;
import koopamillion.mymod.solder.BlockEnderSolder;
import koopamillion.mymod.solder.BlockHCL;
import koopamillion.mymod.solder.BlockSilverSolder;
import koopamillion.mymod.soldertable.BlockSolderPart;
import koopamillion.mymod.soldertable.BlockSolderTable;
import koopamillion.mymod.acidbath.BlockAcidBath;
import koopamillion.mymod.furnace.BlockFurnace;
import koopamillion.mymod.solder.BlockSolder;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModBlocks {

    @GameRegistry.ObjectHolder("ekooprebooted:furnace")
    public static BlockFurnace blockFurnace;

    @GameRegistry.ObjectHolder("ekooprebooted:solder")
    public static BlockSolderTable blockSolder;


   @GameRegistry.ObjectHolder("ekooprebooted:chicken")
    public static BlockChickenPlushy blockChicken;

    @GameRegistry.ObjectHolder("ekooprebooted:machineframe")
    public static BlockMachineFrame blockMachineFrame;
    @GameRegistry.ObjectHolder("ekooprebooted:augstone")
    public static BlockAugmentedStone blockAugmentedStone;
    @GameRegistry.ObjectHolder("ekooprebooted:solderpart")
    public static BlockSolderPart blockSolderPart;
    @GameRegistry.ObjectHolder("ekooprebooted:saturator")
    public static BlockSaturator blockSaturator;
    @GameRegistry.ObjectHolder("ekooprebooted:solderfluid")
    public static BlockSolder blockSolderFluid;
    @GameRegistry.ObjectHolder("ekooprebooted:enderfluid")
    public static BlockEnderSolder blockEnderFluid;
    @GameRegistry.ObjectHolder("ekooprebooted:hcl")
    public static BlockHCL blockHCL;
    @GameRegistry.ObjectHolder("ekooprebooted:silversolder")
    public static BlockSilverSolder blockSilverSolder;
    @GameRegistry.ObjectHolder("ekooprebooted:generator")
    public static BlockGenerator blockGenerator;

    @GameRegistry.ObjectHolder("ekooprebooted:acidbath")
    public static BlockAcidBath blockAcidbath;
    @GameRegistry.ObjectHolder("ekooprebooted:dnaextractor")
    public static BlockDNAExtractor blockDNAExtractor;

    @GameRegistry.ObjectHolder("ekooprebooted:centrifuge")
    public static BlockCentrifuge blockCentrifuge;

    @GameRegistry.ObjectHolder("ekooprebooted:motor")
    public static BlockMotor blockMotor;

    @GameRegistry.ObjectHolder("ekooprebooted:advancedcasing")
    public static BlockAdvancedCasing advancedCasing;
    @GameRegistry.ObjectHolder("ekooprebooted:advancedmachineframe")
    public static BlockAdvancedMachineFrame advancedMachineFrame;


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
        blockCentrifuge.initModel();
        blockDNAExtractor.initModel();
        blockGenerator.initModel();
        blockMotor.initModel();
        blockAugmentedStone.initModel();
        blockSilverSolder.initModel();
        advancedCasing.initModel();
        advancedMachineFrame.initModel();

    }




}
