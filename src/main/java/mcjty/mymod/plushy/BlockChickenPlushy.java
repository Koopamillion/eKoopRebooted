package mcjty.mymod.plushy;

import mcjty.mymod.MyMod;
import mcjty.mymod.furnace.BlockFurnace;
import mcjty.mymod.furnace.TileFurnace;
import mcjty.mymod.sound.SoundFurnace;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.Explosion;
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

import static mcjty.mymod.furnace.BlockFurnace.STATE;


public class BlockChickenPlushy extends Block implements ITileEntityProvider {
//(0.250D,0,0.1875D,0.3125D,0.625D,0.0625D);
    public static final AxisAlignedBB CHICKEN_AABB= new AxisAlignedBB(0.9375D,0,0.9375D,0.0625D,0.625D,0.0625D);

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public static final ResourceLocation chicken = new ResourceLocation(MyMod.MODID, "chicken");




    public BlockChickenPlushy() {
        super(Material.CLOTH); //super fetches the material.Iron from the block class (vanilla)
        // mymod:charger
        setRegistryName(chicken);
        setUnlocalizedName(MyMod.MODID + ".chicken");
        setHarvestLevel("pickaxe", 0);
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
        world.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_HURT, SoundCategory.BLOCKS, 0.7f, 1.0f,false);
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
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_AMBIENT, SoundCategory.BLOCKS, 0.7f, 1.0f,false);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
       // return this.getDefaultState().withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer));
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
    public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_DEATH, SoundCategory.BLOCKS, 1.0f, 1.0f,false);
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.ENTITY_CHICKEN_DEATH, SoundCategory.BLOCKS, 1.0f, 1.0f,false);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {

        TileEntity te = worldIn.getTileEntity(pos);
        IItemHandler itemHandler = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        if(te instanceof TileChickenPlushy){
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
