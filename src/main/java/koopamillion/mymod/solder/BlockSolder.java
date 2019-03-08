package koopamillion.mymod.solder;

import koopamillion.mymod.ModLiquids;
import koopamillion.mymod.MyMod;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSolder extends BlockFluidClassic {
    public static final ResourceLocation SOLDERLIQUID = new ResourceLocation(MyMod.MODID, "solderfluid");

    public BlockSolder() {
        super(ModLiquids.solderfluid, Material.LAVA);
        setCreativeTab(MyMod.tabEKoop);
        setTranslationKey(MyMod.MODID + ".solderfluid");
        setRegistryName(SOLDERLIQUID);
        setTemperature(1000);
    }



    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelResourceLocation fluidLocation = new ModelResourceLocation(SOLDERLIQUID, "fluid");

        StateMapperBase customState = new StateMapperBase() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
                return fluidLocation;
            }
        };
        ModelLoader.setCustomStateMapper(this, customState);
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(SOLDERLIQUID, "inventory"));
    }



}
