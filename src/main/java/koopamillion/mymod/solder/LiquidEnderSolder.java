package koopamillion.mymod.solder;

import koopamillion.mymod.MyMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class LiquidEnderSolder extends Fluid {
    public LiquidEnderSolder(){
        super("enderfluid",
                new ResourceLocation(MyMod.MODID,"blocks/enderfluid_still"),
                new ResourceLocation(MyMod.MODID,"blocks/enderfluid_flow"));
        setTemperature(1100);
    }
}
