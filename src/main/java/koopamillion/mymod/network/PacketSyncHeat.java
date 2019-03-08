package koopamillion.mymod.network;

import io.netty.buffer.ByteBuf;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.tools.IHeatContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncHeat implements IMessage {

    private int heat;

    @Override
    public void fromBytes(ByteBuf buf) {
        heat = buf.readInt();

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(heat);


    }

    // You need this constructor!
    public PacketSyncHeat() {
    }

    public PacketSyncHeat(int heat) {
        this.heat = heat;
    }

    public static class Handler implements IMessageHandler<PacketSyncHeat, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncHeat message, MessageContext ctx) {
            MyMod.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }



        private void handle(PacketSyncHeat message, MessageContext ctx) {
            EntityPlayer player = MyMod.proxy.getClientPlayer();
            if (player.openContainer instanceof IHeatContainer) {
                ((IHeatContainer) player.openContainer).syncHeat(message.heat);
            }
        }
    }
}