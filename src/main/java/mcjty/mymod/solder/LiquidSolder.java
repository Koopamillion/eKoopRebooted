package mcjty.mymod.solder;

import mcjty.mymod.MyMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

public class LiquidSolder extends Fluid {
    public LiquidSolder(){
        super("solderfluid",
                new ResourceLocation(MyMod.MODID,"blocks/solderfluid_still"),
        new ResourceLocation(MyMod.MODID,"blocks/solderfluid_flow"));
    }
}
