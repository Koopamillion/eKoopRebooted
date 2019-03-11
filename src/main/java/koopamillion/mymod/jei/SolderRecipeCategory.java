package koopamillion.mymod.jei;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.ModLiquids;
import koopamillion.mymod.MyMod;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import scala.actors.threadpool.Arrays;

import javax.annotation.Nonnull;
import java.util.List;

public class SolderRecipeCategory implements IRecipeCategory<SolderRecipeWrapper> {

    private final IDrawable background;



    public SolderRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(MyMod.MODID, "textures/gui/solderjei.png");
        background = guiHelper.createDrawable(location, 3, 18, 170, 70);
    }

    @Nonnull
    @Override
    public String getUid() {
        return JeiPlugin.SOLDER_ID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Soldering Table";
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SolderRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

        for(int i = 0; i < 3; i++) {
            guiItemStacks.init(i, true, 60 + i * 18, 6);

        }
        for(int i = 0; i < 3; i++) {
            guiItemStacks.init(i+ 3, true, 60 + i * 18, 6 + 18);
        }
        for(int i = 0; i < 3; i++) {
            guiItemStacks.init(i+ 6, true, 60 + i * 18, 6 + 36);
        }
        guiItemStacks.init(10, false, 141, 6+18);
guiFluidStacks.init(0, false, 7, 10, 16 ,45 ,4000 ,true ,null);
        guiItemStacks.init(11, false, 33, 6+18);




        List<ItemStack> inputs = ingredients.getInputs(VanillaTypes.ITEM).get(0);


        List<FluidStack> inputss = ingredients.getInputs(VanillaTypes.FLUID).get(0);
        List<ItemStack> outputs = ingredients.getOutputs(VanillaTypes.ITEM).get(0);

        guiItemStacks.set(0, recipeWrapper.theRecipe.getInput1().copy());
        guiItemStacks.set(1, recipeWrapper.theRecipe.getInput2().copy());
        guiItemStacks.set(2, recipeWrapper.theRecipe.getInput3().copy());
        guiItemStacks.set(3, recipeWrapper.theRecipe.getInput4().copy());
        guiItemStacks.set(4, recipeWrapper.theRecipe.getInputboard().copy());
        guiItemStacks.set(5, recipeWrapper.theRecipe.getInput6().copy());
        guiItemStacks.set(6, recipeWrapper.theRecipe.getInput7().copy());
        guiItemStacks.set(7, recipeWrapper.theRecipe.getInput8().copy());
        guiItemStacks.set(8, recipeWrapper.theRecipe.getInput9().copy());
        guiItemStacks.set(11, recipeWrapper.theRecipe.getInput11().copy());


   /*         guiItemStacks.set(0, inputs.get(0).copy());
        guiItemStacks.set(1, inputs.get(1).copy());
        guiItemStacks.set(2, inputs.get(2).copy());
        guiItemStacks.set(3, inputs.get(3).copy());
        guiItemStacks.set(4, inputs.get(4).copy());
        guiItemStacks.set(5, inputs.get(5).copy());
        guiItemStacks.set(6, inputs.get(6).copy());
        guiItemStacks.set(7, inputs.get(7).copy());
        guiItemStacks.set(8, inputs.get(8).copy());*/
      //  guiItemStacks.set(9, inputs.get(9));

        guiFluidStacks.set(0, inputss);

        guiItemStacks.set(10, outputs);
    }

    @Override
    public String getModName() {
        return MyMod.MODNAME;
    }
}