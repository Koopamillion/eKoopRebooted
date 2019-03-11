package koopamillion.mymod.saturator;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.generators.TileGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

public class BlockSaturator extends Block implements ITileEntityProvider {


    public static final AxisAlignedBB SAT_AABB= new AxisAlignedBB(0.9375D,0,0.9375D,0.0625D,0.5D,0.0625D);

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static boolean keepInventory;
    //  public static final PropertyEnum<GeneratorState> STATE = PropertyEnum.<FurnaceState>create("state", GeneratorState.class);
    public static final ResourceLocation saturator = new ResourceLocation(MyMod.MODID, "saturator");

    /*   ResourceLocation location = new ResourceLocation("mymod", "furnaceActive");
       SoundEvent event = new SoundEvent(location);*/
    private TileSaturator te;

    public BlockSaturator() {
        super(Material.IRON); //super fetches the material.Iron from the block class (vanilla)
        // mymod:furnace
        setRegistryName(saturator);
        setTranslationKey(MyMod.MODID + ".saturator");
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(1);

//        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));


    }

  /*  @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        final TileEntity tile = worldIn.getTileEntity(pos);
        if(tile instanceof TileFurnace){
            if(((TileFurnace) tile).getState()==WORKING){
                worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundFurnace.furnaceActive, SoundCategory.BLOCKS, 1.0F, 1.0F, false);

            }
        }

    }*/



    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return SAT_AABB;

    }


    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return SAT_AABB;
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSaturator();
    }


    @Override

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Only execute on the server
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);

        if (!(te instanceof TileSaturator)) {
            return false;
        }
        world.notifyBlockUpdate(pos, state, state, 2);
        if(player.getHeldItem(hand).getItem() == ModItems.energy && !((TileSaturator) te).getDebug()){
            ((TileSaturator) te).setDebug(true);
            return true;
        }
        if(player.getHeldItem(hand).getItem() == ModItems.energy && ((TileSaturator) te).getDebug()){
            ((TileSaturator) te).setDebug(false);
            return true;
        }

        player.openGui(MyMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());





        return true;
    }



    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
          ClientRegistry.bindTileEntitySpecialRenderer(TileSaturator.class, new SaturatorTESR());
    }



 /*   @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (te instanceof TileGenerator) {
            return state.withProperty(STATE, ((TileGenerator) te).getState());
        }
        return super.getActualState(state, world, pos);
    }*/




    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if(te instanceof TileSaturator){
            for(int slot=0; slot <6;){
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



    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }


    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}