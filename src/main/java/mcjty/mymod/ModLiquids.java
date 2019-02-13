package mcjty.mymod;

import mcjty.mymod.solder.LiquidSolder;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ModLiquids {
    public static final Fluid solderfluid = new LiquidSolder();

    public static void init() {
        FluidRegistry.registerFluid(solderfluid);
        FluidRegistry.addBucketForFluid(solderfluid);
    }

    public static boolean isValidFloadStack(FluidStack stack){
        return getFluidFromStack(stack) == solderfluid;
    }

    public static Fluid getFluidFromStack(FluidStack stack){
        return stack == null ? null : stack.getFluid();
    }

    public static String getFluidName(FluidStack stack){
        Fluid fluid = getFluidFromStack(stack);
        return getFluidName(fluid);
    }

    public static String getFluidName(Fluid fluid){
        return fluid == null ? "null" : fluid.getName();
    }

    public static int getAmount(FluidStack stack){
        return stack == null ? 0 : stack.amount;
    }
}
