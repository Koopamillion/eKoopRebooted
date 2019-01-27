package mcjty.mymod.charger;

import mcjty.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import net.minecraft.block.properties.PropertyDirection;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import net.minecraftforge.client.model.ModelLoader;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;


public class BlockDroneCharger extends Block {

    public static final AxisAlignedBB CHARGER_AABB = new AxisAlignedBB(0.125D,0,0.125D,0.875D,0.75D,0.875D);

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public static final ResourceLocation aCharger = new ResourceLocation(MyMod.MODID, "charger");



    public BlockDroneCharger() {
        super(Material.IRON); //super fetches the material.Iron from the block class (vanilla)
        // mymod:charger
        setRegistryName(aCharger);
        setUnlocalizedName(MyMod.MODID + ".charger");
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(1);


    //    setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

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
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CHARGER_AABB;

    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return CHARGER_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }



    /*   @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }
    //
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }


    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(FACING).getIndex();
    }
*/


}
