package koopamillion.mymod.jei;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.ModLiquids;
import koopamillion.mymod.MyMod;
import koopamillion.mymod.crafting.SolderRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;


import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SolderRecipeWrapper implements IRecipeWrapper {

    private final List<List<ItemStack>> inputs;
    private final List<FluidStack> inputss;
    private final ItemStack output;

    public final SolderRecipe theRecipe;

    public SolderRecipeWrapper(SolderRecipe recipe) {
        this.inputs = new ArrayList<>();
        this.inputss = new ArrayList<>();
        this.theRecipe = recipe;

        inputs.add(getListForElement(recipe.getInput1().getMatchingStacks().clone()));
        inputs.add(getListForElement(recipe.getInput2().getMatchingStacks().clone()));
        inputs.add(getListForElement(recipe.getInput3().getMatchingStacks().clone()));
        inputs.add(getListForElement(recipe.getInput4().getMatchingStacks().clone()));
        inputs.add(getListForElement(recipe.getInputboard().getMatchingStacks().clone()));
        inputs.add(getListForElement(recipe.getInput6().getMatchingStacks().clone()));
        inputs.add(getListForElement(recipe.getInput7().getMatchingStacks().clone()));
        inputs.add(getListForElement(recipe.getInput8().getMatchingStacks().clone()));
        inputs.add(getListForElement(recipe.getInput9().getMatchingStacks().clone()));

       /* inputs.add(recipe.getInput1().getMatchingStacks().clone());
        inputs.add(recipe.getInput2().getMatchingStacks().clone());
        inputs.add(recipe.getInput3().getMatchingStacks().clone());
        inputs.add(recipe.getInput4().getMatchingStacks().clone());
        inputs.add(recipe.getInputboard().getMatchingStacks().clone());
        inputs.add(recipe.getInput6().getMatchingStacks().clone());
        inputs.add(recipe.getInput7().getMatchingStacks().clone());
        inputs.add(recipe.getInput8().getMatchingStacks().clone());
        inputs.add(recipe.getInput9().getMatchingStacks().clone());*/



      inputss.add(recipe.getInput10().copy());

     //   inputs.add(recipe.getInput11().getMatchingStacks().clone());
        this.output = recipe.getOutput().copy();



    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, output);
        ingredients.setInputs(VanillaTypes.FLUID, inputss);
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

    }



    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }



    public static List<ItemStack> getListForElement(ItemStack[] a){

        return Arrays.asList(a);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }
}