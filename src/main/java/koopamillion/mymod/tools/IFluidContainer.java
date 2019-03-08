package koopamillion.mymod.tools;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

public interface IFluidContainer {
    void syncTank(NBTTagCompound fluid);
}
