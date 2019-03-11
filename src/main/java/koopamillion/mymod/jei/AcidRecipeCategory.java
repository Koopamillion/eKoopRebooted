package koopamillion.mymod.jei;

import koopamillion.mymod.ModLiquids;
import koopamillion.mymod.MyMod;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.List;

public class AcidRecipeCategory  implements IRecipeCategory<AcidRecipeWrapper> {

    private final IDrawable background;



    public AcidRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(MyMod.MODID, "textures/gui/acidjei.png");
        background = guiHelper.createDrawable(location, 3, 18, 170, 70);
    }

    @Nonnull
    @Override
    public String getUid() {
        return JeiPlugin.ACID_ID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Fluid Bath";
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
    public void setRecipe(IRecipeLayout recipeLayout, AcidRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
        IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();


        guiItemStacks.init(0, true, 10, 10); //input item
        guiItemStacks.init(1, false, 140, 10); //output item
        guiItemStacks.init(2, true, 85, 44);
        guiItemStacks.init(3, true, 85, 44);


        guiFluidStacks.init(4, true, 86, 11, 16 ,16 ,1000 ,true ,null); //input fluid
        guiFluidStacks.init(5, false, 141, 45, 16 ,16 ,1000 ,true ,null);

        guiItemStacks.set(0, ingredients.getInputs(VanillaTypes.ITEM).get(0));
        guiFluidStacks.set(4, ingredients.getInputs(VanillaTypes.FLUID).get(0)); //fluid input
        guiFluidStacks.set(5, ingredients.getOutputs(VanillaTypes.FLUID).get(0)); //fluid output
        guiItemStacks.set(1, ingredients.getOutputs(VanillaTypes.ITEM).get(0));


        guiItemStacks.set(2, recipeWrapper.theRecipe.getInput3());
        guiItemStacks.set(3, recipeWrapper.theRecipe.getInput4());


    /*    guiItemStacks.set(3, ingredients.getInputs(VanillaTypes.ITEM).get(2));

        if(recipeWrapper.theRecipe.getBool()){
            guiItemStacks.set(2, new ItemStack());
        }*/






    }

    @Override
    public String getModName() {
        return MyMod.MODNAME;
    }
}