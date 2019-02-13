package mcjty.mymod.solder;

import mcjty.mymod.ModLiquids;
import mcjty.mymod.MyMod;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        setUnlocalizedName(MyMod.MODID + ".solderfluid");
        setRegistryName(SOLDERLIQUID);
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

    @Override
    public BlockFluidBase setTemperature(int temperature) {
        return super.setTemperature(1000);
    }

}
