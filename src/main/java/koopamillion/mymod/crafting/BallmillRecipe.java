package koopamillion.mymod.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class BallmillRecipe {
    private final Ingredient input1;
    private final ItemStack output1;
    private final ItemStack output2;
    private final ItemStack output3;

    private final int chance1;
    private final int chance2;
    private final int chance3;

    private final int tier;

    private final int RPM;


    public BallmillRecipe(Ingredient input1, ItemStack output1, ItemStack output2, ItemStack output3,

                          int chance1, int chance2, int chance3,
                       int RPM, int tier) {
        this.input1 = input1;
        this.output1 = output1;
        this.output2 = output2;
        this.output3 = output3;
        this.chance1 = chance1;
        this.chance2 = chance2;
        this.chance3 = chance3;
        this.tier = tier;


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


    public int getTier(){return tier;}
    public ItemStack getOutput1() { return output1; }

    public ItemStack getOutput2() {
        return output2;
    }

    public ItemStack getOutput3() {
        return output3;
    }





    public int getRPM() {
        return RPM;
    }
}
