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

public class BlockAugmentedStone extends Block {

    public static final ResourceLocation augstone = new ResourceLocation(MyMod.MODID, "augstone");

    public BlockAugmentedStone() {
        super(Material.ROCK); //super fetches the material.Iron from the block class (vanilla)
        // mymod:charger
        setRegistryName(augstone);
        setTranslationKey(MyMod.MODID + ".augstone");
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(50);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }

}
