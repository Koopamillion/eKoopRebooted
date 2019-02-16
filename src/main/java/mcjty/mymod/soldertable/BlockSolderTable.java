package mcjty.mymod.soldertable;

import mcjty.mymod.ModBlocks;
import mcjty.mymod.MyMod;
import mcjty.mymod.furnace.TileFurnace;
import mcjty.mymod.plushy.TileChickenPlushy;
import mcjty.mymod.tools.MultiBlockTools;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityBedRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

public class BlockSolderTable extends Block implements ITileEntityProvider {
 //   public static final AxisAlignedBB CHICKEN_AABB= new AxisAlignedBB(0.9375D,0,0.9375D,0.0625D,0.625D,0.0625D);



    public static final ResourceLocation solder = new ResourceLocation(MyMod.MODID, "solder");
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final int testid = 342;

   // public static final PropertyEnum<SolderPartIndex> FORMED = PropertyEnum.<SolderPartIndex>create("formed", SolderPartIndex.class);


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
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        // return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }



    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileSolderTable();
    }

    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    //    world.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_HURT, SoundCategory.BLOCKS, 0.7f, 1.0f,false);
        // Only execute on the server
      /*  if (player.getHeldItem(hand).getItem() == Items.STICK) {
            toggleMultiBlock(world, pos, state, player);
            return true;
        }*/
        // Only work if the block is formed
    /*    if (state.getBlock() == ModBlocks.blockSolder && state.getValue(FORMED) != SolderPartIndex.UNFORMED) {
            player.openGui(MyMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        } else {
            return false;
        }*/
     /*   TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileSolderTable)) {
            return false;
        }
        player.openGui(MyMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        return true;*/
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
        ClientRegistry.bindTileEntitySpecialRenderer(TileSolderTable.class, new SolderTESR());

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
    public int getMetaFromState(IBlockState state){
        return state.getValue(FACING).getHorizontalIndex();
    }



    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta & 7));
    }


   /* public static void toggleMultiBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        // Form or break the multiblock
        if (!world.isRemote) {
            SolderPartIndex formed = state.getValue(FORMED);
            if (formed == SolderPartIndex.UNFORMED) {
                if (MultiBlockTools.formMultiblock(SolderMultiBlock.INSTANCE, world, pos)) {
                    player.sendStatusMessage(new TextComponentString(TextFormatting.GREEN + "Made a superchest!"), false);
                } else {
                    player.sendStatusMessage(new TextComponentString(TextFormatting.RED + "Could not form superchest!"), false);
                }
            } else {
                if (!MultiBlockTools.breakMultiblock(SolderMultiBlock.INSTANCE, world, pos)) {
                    player.sendStatusMessage(new TextComponentString(TextFormatting.RED + "Not a valid superchest!"), false);
                }
            }
        }
    }

    public static boolean ifFormedSolderController(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == ModBlocks.blockSolder && state.getValue(FORMED) != SolderPartIndex.UNFORMED;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if(!worldIn.isRemote){
            MultiBlockTools.breakMultiblock(SolderMultiBlock.INSTANCE, worldIn, pos);


        }
    }

    @Nullable
    public static BlockPos getControllerPos(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == ModBlocks.blockSolder && state.getValue(BlockSolderTable.FORMED) != SolderPartIndex.UNFORMED) {
            return pos;
        }
        if (state.getBlock() == ModBlocks.blockSolderPart && state.getValue(BlockSolderTable.FORMED) != SolderPartIndex.UNFORMED) {
            SolderPartIndex index = state.getValue(BlockSolderTable.FORMED);
            // This index indicates where in the superblock this part is located. From this we can find the location of the bottom-left coordinate
            BlockPos bottomLeft = pos.add(-index.getDx(), -index.getDy(), -index.getDz());
            for (SolderPartIndex idx : SolderPartIndex.VALUES) {
                if (idx != SolderPartIndex.UNFORMED) {
                    BlockPos p = bottomLeft.add(idx.getDx(), idx.getDy(), idx.getDz());
                    if (ifFormedSolderController(world, p)) {
                        return p;
                    }
                }
            }

        }
        return null;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FORMED);
    }
*/

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

/*    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;

    }*/




    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity te = worldIn.getTileEntity(pos);
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if(te instanceof TileSolderTable){
            for(int slot=0; slot < itemHandler.getSlots(); slot++){
                ItemStack stack = itemHandler.getStackInSlot(slot);
                if (!stack.isEmpty()) {
                    EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), stack);
                    worldIn.spawnEntity(item);
                    if(worldIn.spawnEntity(item) == true)
                        stack.setCount(0);
                }

            }
            super.breakBlock(worldIn, pos, state);
        }
    }
}
