package mcjty.mymod.proxy;

import mcjty.mymod.furnace.ContainerFurnace;
import mcjty.mymod.furnace.GuiFurnace;
import mcjty.mymod.furnace.TileFurnace;
import mcjty.mymod.plushy.ContainerChickenPlushy;
import mcjty.mymod.plushy.GuiChickenPlushy;
import mcjty.mymod.plushy.TileChickenPlushy;
import mcjty.mymod.soldertable.ContainerSolderTable;
import mcjty.mymod.soldertable.GuiSolderTable;
import mcjty.mymod.soldertable.TileSolderTable;
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
        if (te instanceof TileChickenPlushy) {
            return new ContainerChickenPlushy(player.inventory, (TileChickenPlushy) te);
        }
        if (te instanceof TileSolderTable) {
            return new ContainerSolderTable(player.inventory, (TileSolderTable) te);
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
        if (te instanceof TileChickenPlushy) {
            TileChickenPlushy containerTileEntity = (TileChickenPlushy) te;
            return new GuiChickenPlushy(containerTileEntity, new ContainerChickenPlushy(player.inventory, containerTileEntity));
        }
        if (te instanceof TileSolderTable) {
            TileSolderTable containerTileEntity = (TileSolderTable) te;
            return new GuiSolderTable(containerTileEntity, new ContainerSolderTable(player.inventory, containerTileEntity));
        }
        return null;
    }
}
