package mcjty.mymod.soldertable;

import mcjty.mymod.MyMod;
import mcjty.mymod.plushy.TileChickenPlushy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BlockSolderTable extends Block implements ITileEntityProvider {
 //   public static final AxisAlignedBB CHICKEN_AABB= new AxisAlignedBB(0.9375D,0,0.9375D,0.0625D,0.625D,0.0625D);

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public static final ResourceLocation solder = new ResourceLocation(MyMod.MODID, "solder");




    public BlockSolderTable() {
        super(Material.CLOTH); //super fetches the material.Iron from the block class (vanilla)
        // mymod:charger
        setRegistryName(solder);
        setUnlocalizedName(MyMod.MODID + ".solder");
        setHarvestLevel("axe", 1);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(1);



        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSolderTable();
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    //    world.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_HURT, SoundCategory.BLOCKS, 0.7f, 1.0f,false);
        // Only execute on the server
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileSolderTable)) {
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
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
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
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 7));
    }


    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(FACING).getHorizontalIndex();
    }


    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        TileEntity te = worldIn.getTileEntity(pos);
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        if(te instanceof TileSolderTable){
            for(int slot=0; slot <9;){
                ItemStack stack = itemHandler.getStackInSlot(slot);
                if (!stack.isEmpty()) {
                    EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                    worldIn.spawnEntity(item);
                    if(worldIn.spawnEntity(item) == true)
                        stack.setCount(0);
                }
                slot++;
            }
            super.breakBlock(worldIn, pos, state);
        }
    }
}
