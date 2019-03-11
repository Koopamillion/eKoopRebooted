package koopamillion.mymod.crafting;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class SolderRecipe {

    private final ItemStack input4;
    private final ItemStack inputboard;
    private final ItemStack input1;
    private final ItemStack input2;
    private final ItemStack input3;
    private final ItemStack input6;
    private final ItemStack input7;
    private final ItemStack input8;
    private final ItemStack input9;
    private final FluidStack input10;
    private final ItemStack output;
    private final ItemStack input11;

    public SolderRecipe(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack inputboard, ItemStack input6, ItemStack input7, ItemStack input8, ItemStack input9, ItemStack output, FluidStack input10, ItemStack input11) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3 = input3;
        this.input4 = input4;
        this.input6 = input6;
        this.input7 = input7;
        this.input8 = input8;
        this.input9 = input9;
        this.inputboard = inputboard;
        this.output = output;
        this.input10 = input10;
        this.input11 = input11;
    }

    public ItemStack getInput1() {
        return input1;
    }
    public ItemStack getInput2() {
        return input2;
    }
    public ItemStack getInput3() {
        return input3;
    }
    public ItemStack getInput4() {
        return input4;
    }
    public ItemStack getInputboard() {
        return inputboard;
    }
    public ItemStack getInput6() {
        return input6;
    }
    public ItemStack getInput7() {
        return input7;
    }
    public ItemStack getInput8() {
        return input8;
    }
    public ItemStack getInput9() {
        return input9;
    }
    public ItemStack getOutput() {
        return output;
    }
    public FluidStack getInput10(){return input10;}

    public ItemStack getInput11() {
        return input11;
    }
    public int getInput10Amount(){return input10.copy().amount;}
}