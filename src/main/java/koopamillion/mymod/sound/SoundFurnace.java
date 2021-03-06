package koopamillion.mymod.sound;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class SoundFurnace {
    private SoundFurnace(){}
    public static final SoundFurnace INSTANCE = new SoundFurnace();
    public static SoundEvent furnaceActive;

    public static void preInit() {

        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }

    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> registry){
        furnaceActive = registerSound("furnaceactive");
    }

    public static SoundEvent registerSound(String name)
    {
        ResourceLocation loc = new ResourceLocation("ekooprebooted", name);
        SoundEvent event = new SoundEvent(loc).setRegistryName(name);
        ForgeRegistries.SOUND_EVENTS.register(event);
        return event;
    }

}
