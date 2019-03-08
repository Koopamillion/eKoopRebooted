package koopamillion.mymod.network;



import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class Messages {

    public static SimpleNetworkWrapper INSTANCE;

    private static int ID = 0;
    private static int nextID() {
        return ID++;
    }


    //creates a channel (called in commonproxy)
    public static void registerMessages(String channelName) {
        INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(channelName);

        // Server side


        // Client side
        //makes a message using packetsync power
        INSTANCE.registerMessage(PacketSyncPower.Handler.class, PacketSyncPower.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketSyncHeat.Handler.class, PacketSyncHeat.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketSyncTank.Handler.class, PacketSyncTank.class, nextID(), Side.CLIENT);

    }
}