package koopamillion.mymod.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class AcidRecipe {
    private final Ingredient input1;
    private final FluidStack input2;
    private final ItemStack output;
    private final FluidStack output2;
    //has heat below
    private final Boolean bool;
    //has redstone block below
    private final Boolean bool2;
    private final ItemStack input3;
    private final ItemStack input4;

    public AcidRecipe(Ingredient input1, ItemStack output, FluidStack input2, Boolean bool, Boolean bool2, FluidStack output2, ItemStack input3, ItemStack input4) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
        this.bool = bool;
        this.bool2 = bool2;
        this.output2 = output2;
        this.input3 = input3;
        this.input4 = input4;
    }

    public Ingredient getInput1() {
        return input1;
    }
    public FluidStack getInput2(){return input2;}
    public ItemStack getOutput() {
        return output;
    }
    public Boolean getBool(){return bool;}
    public Boolean getBool2(){return bool2;}
    public FluidStack getOutput2(){
        return output2;
    }
    public ItemStack getInput3(){return  input3;}
    public ItemStack getInput4(){return  input4;}


}
