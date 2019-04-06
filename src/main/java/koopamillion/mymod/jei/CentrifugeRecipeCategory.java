package koopamillion.mymod.jei;

import koopamillion.mymod.ModBlocks;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class CentrifugeRecipeCategory  implements IRecipeCategory<CentrifugeRecipeWrapper> {

    private final IDrawable background;



    public CentrifugeRecipeCategory(IGuiHelper guiHelper) {
        ResourceLocation location = new ResourceLocation(MyMod.MODID, "textures/gui/centrifugejei.png");
        background = guiHelper.createDrawable(location, 0, 0, 180, 93);
    }

    @Nonnull
    @Override
    public String getUid() {
        return JeiPlugin.CENTRIFUGE_ID;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return "Centrifuge";
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return background;
    }



    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CentrifugeRecipeWrapper recipeWrapper, IIngredients ingredients) {

        IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();


        guiItemStacks.init(0, false, 80, 12);

        for(int i = 0; i < 9; i++) {
            guiItemStacks.init(i+1, true, 9 + i * 18, 57);

        }

        guiItemStacks.init(11,false,9,12);





        List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);


        List<ItemStack> list = new ArrayList<>();
        list.add(new ItemStack(ModBlocks.blockMotor));


        guiItemStacks.set(0, inputs.get(0));

        for(int i = 0; i < 9; i++){
            guiItemStacks.set(i+1, ingredients.getOutputs(VanillaTypes.ITEM).get(i));
        }
        guiItemStacks.set(11, list);



    }

    @Override
    public String getModName() {
        return MyMod.MODNAME;
    }
}