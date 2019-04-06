package koopamillion.mymod.solder;

import koopamillion.mymod.MyMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class LiquidSilverSolder extends Fluid {
    public LiquidSilverSolder(){
        super("silversolder",
                new ResourceLocation(MyMod.MODID,"blocks/silversolder_still"),
                new ResourceLocation(MyMod.MODID,"blocks/silversolder_flow"));
    }

}