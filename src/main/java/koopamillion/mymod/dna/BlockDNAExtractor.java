package koopamillion.mymod.dna;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.furnace.FurnaceState;
import koopamillion.mymod.furnace.TileFurnace;
import koopamillion.mymod.saturator.TileSaturator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BlockDNAExtractor  extends Block implements ITileEntityProvider {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<DNAState> STATE = PropertyEnum.<DNAState>create("state", DNAState.class);
    private static boolean keepInventory;
    public static final ResourceLocation dnaextractor = new ResourceLocation(MyMod.MODID, "dnaextractor");

    /*   ResourceLocation location = new ResourceLocation("mymod", "furnaceActive");
       SoundEvent event = new SoundEvent(location);*/
    private TileDNAExtractor te;

    public BlockDNAExtractor() {
        super(Material.IRON); //super fetches the material.Iron from the block class (vanilla)
        // mymod:furnace
        setRegistryName(dnaextractor);
        setTranslationKey(MyMod.MODID + ".dnaextractor");
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(1);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));


    }



    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileDNAExtractor();
    }



    @Override

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Only execute on the server
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileDNAExtractor)) {
            return false;
        }
        world.notifyBlockUpdate(pos, state, state, 2);
        if(player.getHeldItem(hand).getItem() == ModItems.energy && !((TileDNAExtractor) te).getDebug()){
            ((TileDNAExtractor) te).setDebug(true);
            return true;
        }
        if(player.getHeldItem(hand).getItem() == ModItems.energy && ((TileDNAExtractor) te).getDebug()){
            ((TileDNAExtractor) te).setDebug(false);
            return true;
        }
        world.notifyBlockUpdate(pos, state, state, 2);
        player.openGui(MyMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }



    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }



    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (te instanceof TileDNAExtractor) {
            return state.withProperty(STATE, ((TileDNAExtractor) te).getState());
        }
        return super.getActualState(state, world, pos);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    //
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, STATE);
    }


    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta & 7));
    }





    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if(te instanceof TileDNAExtractor){
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


}