package koopamillion.mymod.jei;

import com.sun.org.apache.xpath.internal.operations.Bool;
import koopamillion.mymod.crafting.AcidRecipe;
import koopamillion.mymod.crafting.SolderRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AcidRecipeWrapper implements IRecipeWrapper {


  //  private final ItemStack output;
    private final FluidStack outputfluid;
    private final ItemStack output;


  //  private final List<ItemStack> inputl;
 //   private final List<FluidStack> inputfl;

    private final ItemStack input;
    private final FluidStack inputfluid;
  //  private final ItemStack inputf;
//    private final ItemStack inpute;


    public final AcidRecipe theRecipe;

    public AcidRecipeWrapper(AcidRecipe recipe) {
        this.theRecipe = recipe;

    //    this.outputss = recipe.getOutput2().copy(); //fluid //might be null
    //    this.output = recipe.getOutput().copy();  //might be null

        this.input = recipe.getInput1();
        this.inputfluid = recipe.getInput2();
        this.outputfluid = recipe.getOutput2();
        this.output = recipe.getOutput();
       // this.inputf = recipe.getInput3();
       // this.inpute = recipe.getInput3();

    //    this.inputl = new ArrayList<>();
     //   this.inputfl = new ArrayList<>();

     //   inputl.add(recipe.getInput1().copy());
     //   inputl.add(recipe.getInput3().copy()); //might be null
 //       inputl.add(recipe.getInput4().copy()); //might be null
      //  inputfl.add(recipe.getInput2().copy());
//
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
     //   ingredients.setInputs(VanillaTypes.ITEM, inputl);
     //   ingredients.setInputs(VanillaTypes.FLUID, inputfl);
     //   ingredients.setOutput(VanillaTypes.ITEM, output);
     //   ingredients.setOutput(VanillaTypes.FLUID, outputss);
        ingredients.setInput(VanillaTypes.ITEM, input);
        ingredients.setInput(VanillaTypes.FLUID, inputfluid);
        ingredients.setOutput(VanillaTypes.FLUID, outputfluid);
        ingredients.setOutput(VanillaTypes.ITEM, output);
       // ingredients.setInputs();
     //   ingredients.setInput(VanillaTypes.ITEM, inpute);


    }



    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
    }



    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }
}