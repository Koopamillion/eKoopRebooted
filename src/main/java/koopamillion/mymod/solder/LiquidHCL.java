package koopamillion.mymod.solder;

import koopamillion.mymod.MyMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class LiquidHCL extends Fluid {
    public LiquidHCL(){
        super("hcl",
                new ResourceLocation(MyMod.MODID,"blocks/hcl_still"),
                new ResourceLocation(MyMod.MODID,"blocks/hcl_flow"));
    }

    public boolean isAcidic(){
        return true;
    }
}