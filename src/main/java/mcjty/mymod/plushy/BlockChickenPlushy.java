package mcjty.mymod.plushy;

import mcjty.mymod.MyMod;
import mcjty.mymod.furnace.TileFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static mcjty.mymod.furnace.BlockFurnace.STATE;


public class BlockChickenPlushy extends Block implements ITileEntityProvider {

    public static final AxisAlignedBB CHICKEN_AABB = new AxisAlignedBB(0.125D,0,0.125D,0.875D,0.75D,0.875D);

    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public static final ResourceLocation chicken = new ResourceLocation(MyMod.MODID, "chicken");



    public BlockChickenPlushy() {
        super(Material.CLOTH); //super fetches the material.Iron from the block class (vanilla)
        // mymod:charger
        setRegistryName(chicken);
        setUnlocalizedName(MyMod.MODID + ".chicken");
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(1);



        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileChickenPlushy();
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Only execute on the server
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileChickenPlushy)) {
            return false;
        }
        player.openGui(MyMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
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
        return CHICKEN_AABB;

    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return CHICKEN_AABB;
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
    }


    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta & 7));
    }


    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(FACING).getIndex();
    }
}
