package koopamillion.mymod.network;

import io.netty.buffer.ByteBuf;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.tools.IFluidContainer;
import koopamillion.mymod.tools.INBTContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketSyncNBT  implements IMessage {

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
    public PacketSyncNBT() {
    }

    public PacketSyncNBT(NBTTagCompound heat) {
        this.heat = heat;
    }

    public static class Handler implements IMessageHandler<PacketSyncNBT, IMessage> {
        @Override
        public IMessage onMessage(PacketSyncNBT message, MessageContext ctx) {
            MyMod.proxy.addScheduledTaskClient(() -> handle(message, ctx));
            return null;
        }



        private void handle(PacketSyncNBT message, MessageContext ctx) {
            EntityPlayer player = MyMod.proxy.getClientPlayer();
            if (player.openContainer instanceof INBTContainer) {
                ((INBTContainer) player.openContainer).syncNBT(message.heat);
            }
        }
    }
}
