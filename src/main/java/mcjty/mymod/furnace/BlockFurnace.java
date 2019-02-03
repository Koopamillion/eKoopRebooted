package mcjty.mymod.furnace;

import javafx.geometry.HorizontalDirection;
import mcjty.mymod.MyMod;
import mcjty.mymod.plushy.TileChickenPlushy;
import mcjty.mymod.sound.SoundFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.*;
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
import java.util.Random;

import static mcjty.mymod.furnace.FurnaceState.WORKING;

public class BlockFurnace extends Block implements ITileEntityProvider {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<FurnaceState> STATE = PropertyEnum.<FurnaceState>create("state", FurnaceState.class);
    private static boolean keepInventory;
    public static final ResourceLocation afurnace = new ResourceLocation(MyMod.MODID, "furnace");
 /*   ResourceLocation location = new ResourceLocation("mymod", "furnaceActive");
    SoundEvent event = new SoundEvent(location);*/
    private TileFurnace te;

    public BlockFurnace() {
        super(Material.IRON); //super fetches the material.Iron from the block class (vanilla)
        // mymod:furnace
        setRegistryName(afurnace);
        setUnlocalizedName(MyMod.MODID + ".furnace");
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(1);

        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));


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



    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileFurnace();
    }



    @Override

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        // Only execute on the server
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileFurnace)) {
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
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntity te = world instanceof ChunkCache ? ((ChunkCache)world).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK) : world.getTileEntity(pos);
        if (te instanceof TileFurnace) {
            return state.withProperty(STATE, ((TileFurnace) te).getState());
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
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 7));
    }





    @Override
    public int getMetaFromState(IBlockState state){
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if(te instanceof TileFurnace){
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
