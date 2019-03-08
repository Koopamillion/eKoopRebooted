package koopamillion.mymod.network;

import io.netty.buffer.ByteBuf;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.tools.IFluidContainer;
import koopamillion.mymod.tools.IHeatContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncTank  implements IMessage {

    private NBTTagCompound heat;

    @Override
    public void fromBytes(ByteBuf buf) {
        heat = ByteBufUtils.readTag(buf);

    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeTag(buf, heat);


    }

    // You need this constructor!
    public PacketSyncTank() {
    }

    public PacketSyncTank(NBTTagCompound heat) {
        this.heat = heat;
    }

    public static class Handler implements IMessageHandler<PacketSyncTank, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncTank message, MessageContext ctx) {
            MyMod.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }



        private void handle(PacketSyncTank message, MessageContext ctx) {
            EntityPlayer player = MyMod.proxy.getClientPlayer();
            if (player.openContainer instanceof IFluidContainer) {
                ((IFluidContainer) player.openContainer).syncTank(message.heat);
            }
        }
    }
}
