package koopamillion.mymod.simpleblocks;

import koopamillion.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAdvancedMachineFrame extends Block {

    public static final ResourceLocation advancedmachineframe = new ResourceLocation(MyMod.MODID, "advancedmachineframe");

    public BlockAdvancedMachineFrame() {
        super(Material.ROCK); //super fetches the material.Iron from the block class (vanilla)
        // mymod:charger
        setRegistryName(advancedmachineframe);
        setTranslationKey(MyMod.MODID + ".advancedmachineframe");
        setHarvestLevel("pickaxe", 3);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(50);
        setLightLevel(5);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }


    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
}
