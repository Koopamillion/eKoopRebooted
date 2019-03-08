package koopamillion.mymod.acidbath;

import koopamillion.mymod.ModLiquids;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.proxy.CommonProxy;
import koopamillion.mymod.solder.LiquidHCL;
import koopamillion.mymod.solder.LiquidSolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockAcidBath extends Block implements ITileEntityProvider {
    private TileAcidBath te;

    protected static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3125D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
    protected static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    protected static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);

    public static final ResourceLocation acidbath = new ResourceLocation(MyMod.MODID, "acidbath");

    public BlockAcidBath(){
        super(Material.IRON); //super fetches the material.Iron from the block class (vanilla)
        // mymod:furnace
        setRegistryName(acidbath);
        setTranslationKey(MyMod.MODID + ".acidbath");
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(2);
    }







    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if ((te instanceof TileAcidBath)) {
            FluidTank tank = ((TileAcidBath)te).getTank();
            FluidStack fluid = ((TileAcidBath)te).getTank().getFluid();
        //    int i = fluid.amount;
            float f = (float)pos.getY() + 12 / 16.0F;

            if (!worldIn.isRemote && fluid != null)
         //   if (!worldIn.isRemote && entityIn.isBurning() && i > 0 && entityIn.getEntityBoundingBox().minY <= (double)f)
            {
                if(fluid.getFluid().getTemperature() <= 320){
                    if(entityIn.isBurning()){
                        entityIn.extinguish();
                    }
                }else if (fluid.getFluid().getTemperature() > 320 && fluid.getFluid().getTemperature() < 350){
                    entityIn.attackEntityFrom(DamageSource.ON_FIRE, 1f);
                    entityIn.setFire(3);
                }else{
                    entityIn.attackEntityFrom(DamageSource.LAVA, 3f);
                    entityIn.setFire(7);
                }
                if(fluid.getFluid() == ModLiquids.solderfluid){
                    entityIn.attackEntityFrom(DamageSource.LAVA, 3f);
                    entityIn.setFire(7);
                }
                if(fluid.getFluid() instanceof LiquidHCL){
                    if(((LiquidHCL) fluid.getFluid()).isAcidic()){
                        entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 2f);
                    }
                }
            }


            }

        }



    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return FULL_BLOCK_AABB;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileAcidBath();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            FluidUtil.interactWithFluidHandler(playerIn, hand, worldIn, pos, facing);

        }
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileAcidBath)) {
            return false;
        }
        worldIn.notifyBlockUpdate(pos, state, state, 2);
        return true;
    }



    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
        ClientRegistry.bindTileEntitySpecialRenderer(TileAcidBath.class, new AcidTESR());
    }



    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    public boolean isFullCube(IBlockState state) {
        return false;
    }

}
