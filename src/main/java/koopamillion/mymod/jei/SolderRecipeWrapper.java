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
import java.util.Collections;
import java.util.List;

public class SolderRecipeWrapper implements IRecipeWrapper {

    private final List<ItemStack> inputs;
    private final List<FluidStack> inputss;
    private final ItemStack output;

    public final SolderRecipe theRecipe;

    public SolderRecipeWrapper(SolderRecipe recipe) {
        this.inputs = new ArrayList<>();
        this.inputss = new ArrayList<>();
        this.theRecipe = recipe;
        inputs.add(recipe.getInput1().copy());
        inputs.add(recipe.getInput2().copy());
        inputs.add(recipe.getInput3().copy());
        inputs.add(recipe.getInput4().copy());
        inputs.add(recipe.getInputboard().copy());
        inputs.add(recipe.getInput6().copy());
        inputs.add(recipe.getInput7().copy());
        inputs.add(recipe.getInput8().copy());
        inputs.add(recipe.getInput9().copy());


      inputss.add(recipe.getInput10().copy());

        inputs.add(recipe.getInput11().copy());
        this.output = recipe.getOutput().copy();

    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, output);
        ingredients.setInputs(VanillaTypes.ITEM, inputs);
        ingredients.setInputs(VanillaTypes.FLUID, inputss);

    }



    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }



    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }
}