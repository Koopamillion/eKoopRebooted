package koopamillion.mymod.proxy;

import koopamillion.mymod.ballmill.ContainerBallmill;
import koopamillion.mymod.ballmill.GuiBallmill;
import koopamillion.mymod.ballmill.TileBallmill;
import koopamillion.mymod.centrifuge.ContainerCentrifuge;
import koopamillion.mymod.centrifuge.GuiCentrifuge;
import koopamillion.mymod.centrifuge.TileCentrifuge;
import koopamillion.mymod.dna.ContainerDNAExtractor;
import koopamillion.mymod.dna.GuiDNAExtractor;
import koopamillion.mymod.dna.TileDNAExtractor;
import koopamillion.mymod.furnace.ContainerFurnace;
import koopamillion.mymod.furnace.GuiFurnace;
import koopamillion.mymod.furnace.TileFurnace;
import koopamillion.mymod.plushy.ContainerChickenPlushy;
import koopamillion.mymod.plushy.TileChickenPlushy;
import koopamillion.mymod.saturator.ContainerSaturator;
import koopamillion.mymod.saturator.GuiSaturator;
import koopamillion.mymod.saturator.TileSaturator;
import koopamillion.mymod.soldertable.ContainerSolderTable;
import koopamillion.mymod.soldertable.GuiSolderTable;
import koopamillion.mymod.soldertable.TileSolderTable;
import koopamillion.mymod.plushy.GuiChickenPlushy;
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
        if (te instanceof TileSaturator) {
            return new ContainerSaturator(player.inventory, (TileSaturator) te);
        }
        if (te instanceof TileDNAExtractor) {
            return new ContainerDNAExtractor(player.inventory, (TileDNAExtractor) te);
        }
        if (te instanceof TileCentrifuge) {
            return new ContainerCentrifuge(player.inventory, (TileCentrifuge) te);
        }
        if (te instanceof TileBallmill) {
            return new ContainerBallmill(player.inventory, (TileBallmill) te);
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

        if (te instanceof TileSaturator) {
            TileSaturator containerTileEntity = (TileSaturator) te;
            return new GuiSaturator(containerTileEntity, new ContainerSaturator(player.inventory, containerTileEntity));
        }
        if (te instanceof TileDNAExtractor) {
            TileDNAExtractor containerTileEntity = (TileDNAExtractor) te;
            return new GuiDNAExtractor(containerTileEntity, new ContainerDNAExtractor(player.inventory, containerTileEntity));
        }
        if (te instanceof TileCentrifuge) {
            TileCentrifuge containerTileEntity = (TileCentrifuge) te;
            return new GuiCentrifuge(containerTileEntity, new ContainerCentrifuge(player.inventory, containerTileEntity));
        }
        if (te instanceof TileBallmill) {
            TileBallmill containerTileEntity = (TileBallmill) te;
            return new GuiBallmill(containerTileEntity, new ContainerBallmill(player.inventory, containerTileEntity));
        }
        return null;
    }
}
