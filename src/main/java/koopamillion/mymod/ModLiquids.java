package koopamillion.mymod;

import koopamillion.mymod.solder.LiquidEnderSolder;
import koopamillion.mymod.solder.LiquidHCL;
import koopamillion.mymod.solder.LiquidSilverSolder;
import koopamillion.mymod.solder.LiquidSolder;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ModLiquids {
    public static final Fluid solderfluid = new LiquidSolder();
    public static final Fluid hcl = new LiquidHCL();
    public static final Fluid enderfluid = new LiquidEnderSolder();
    public static final Fluid silversolder = new LiquidSilverSolder();

    public static void init() {
        FluidRegistry.registerFluid(solderfluid);
        FluidRegistry.addBucketForFluid(solderfluid);
        FluidRegistry.registerFluid(hcl);
        FluidRegistry.addBucketForFluid(hcl);
        FluidRegistry.registerFluid(enderfluid);
        FluidRegistry.addBucketForFluid(enderfluid);
        FluidRegistry.registerFluid(silversolder);
        FluidRegistry.addBucketForFluid(silversolder);
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
