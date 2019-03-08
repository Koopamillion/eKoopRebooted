package koopamillion.mymod.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Helper {
    public static void spawnItemInWorld(World world, ItemStack stack, BlockPos pos) {
        Entity entity = new EntityItem(world, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, stack);
        world.spawnEntity(entity);
    }
    public static void spawnBlockInWorld(World world, Block block, BlockPos pos) {

        world.setBlockState(pos, block.getDefaultState());
    }

}
