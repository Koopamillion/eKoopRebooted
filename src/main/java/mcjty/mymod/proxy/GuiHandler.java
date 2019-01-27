package mcjty.mymod.proxy;

import mcjty.mymod.furnace.ContainerFurnace;
import mcjty.mymod.furnace.GuiFurnace;
import mcjty.mymod.furnace.TileFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileFurnace) {
            return new ContainerFurnace(player.inventory, (TileFurnace) te);
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileFurnace) {
            TileFurnace containerTileEntity = (TileFurnace) te;
            return new GuiFurnace(containerTileEntity, new ContainerFurnace(player.inventory, containerTileEntity));
        }
        return null;
    }
}