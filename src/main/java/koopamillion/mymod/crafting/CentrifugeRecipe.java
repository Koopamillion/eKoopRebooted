package koopamillion.mymod.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.FluidStack;

public class CentrifugeRecipe {
    private final Ingredient input1;
    private final ItemStack output1;
    private final ItemStack output2;
    private final ItemStack output3;
    private final ItemStack output4;
    private final ItemStack output5;
    private final ItemStack output6;
    private final ItemStack output7;
    private final ItemStack output8;
    private final ItemStack output9;

    private final int chance1;
    private final int chance2;
    private final int chance3;
    private final int chance4;
    private final int chance5;
    private final int chance6;
    private final int chance7;
    private final int chance8;
    private final int chance9;


    private final int RPM;


    public CentrifugeRecipe(Ingredient input1, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4, ItemStack output5, ItemStack output6, ItemStack output7, ItemStack output8, ItemStack output9, int chance1, int chance2, int chance3, int chance4, int chance5, int chance6, int chance7, int chance8, int chance9 ,int RPM) {
        this.input1 = input1;
        this.output1 = output1;
        this.output2 = output2;
        this.output3 = output3;
        this.output4 = output4;
        this.output5 = output5;
        this.output6 = output6;
        this.output7 = output7;
        this.output8 = output8;
        this.output9 = output9;


        this.chance1 = chance1;
        this.chance2 = chance2;
        this.chance3 = chance3;
        this.chance4 = chance4;
        this.chance5 = chance5;
        this.chance6 = chance6;
        this.chance7 = chance7;
        this.chance8 = chance8;
        this.chance9 = chance9;


        this.RPM = RPM;
    }

    public Ingredient getInput1() {
        return input1;
    }

    public int getChance1() {
        return chance1;
    }

    public int getChance2() {
        return chance2;
    }

    public int getChance3() {
        return chance3;
    }

    public int getChance4() {
        return chance4;
    }

    public int getChance5() {
        return chance5;
    }

    public int getChance6() {
        return chance6;
    }

    public int getChance7() {
        return chance7;
    }

    public int getChance8() {
        return chance8;
    }

    public int getChance9() {
        return chance9;
    }


    public ItemStack getOutput1() { return output1; }

    public ItemStack getOutput2() {
        return output2;
    }

    public ItemStack getOutput3() {
        return output3;
    }

    public ItemStack getOutput4() {
        return output4;
    }

    public ItemStack getOutput5() { return output5; }

    public ItemStack getOutput6() {
        return output6;
    }

    public ItemStack getOutput7() {
        return output7;
    }

    public ItemStack getOutput8() {
        return output8;
    }

    public ItemStack getOutput9() { return output9; }



    public int getRPM() {
        return RPM;
    }
}
