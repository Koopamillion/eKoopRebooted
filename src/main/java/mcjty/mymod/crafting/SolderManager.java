package mcjty.mymod.crafting;

import mcjty.mymod.ModItems;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolderManager {

    private static boolean isInit = false;
    private static List<SolderRecipe> customRecipeList = new ArrayList<>();

    public static List<SolderRecipe> getCustomRecipeList() {
        if (!isInit) {
            init();
            isInit = true;
        }
        return customRecipeList;
    }

    @Nullable
    public static SolderRecipe getRecipe(ItemStack input1,ItemStack input2,ItemStack input3,ItemStack input4,ItemStack inputboard, ItemStack input6, ItemStack input7, ItemStack input8, ItemStack input9) {
        for (SolderRecipe recipe : getCustomRecipeList()) {
            if (ItemStack.areItemsEqual(input1, recipe.getInput1())
                    &&ItemStack.areItemsEqual(input2, recipe.getInput2())
                    &&ItemStack.areItemsEqual(input3, recipe.getInput3())
                    &&ItemStack.areItemsEqual(input4, recipe.getInput4())
                    &&ItemStack.areItemsEqual(inputboard, recipe.getInputboard())
                    &&ItemStack.areItemsEqual(input6, recipe.getInput6())
                    &&ItemStack.areItemsEqual(input7, recipe.getInput7())
                    &&ItemStack.areItemsEqual(input8, recipe.getInput8())
                    &&ItemStack.areItemsEqual(input9, recipe.getInput9())) {
                return recipe;
            }
        }
        return null;
    }

    private static void init() {
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.REDSTONE), new ItemStack(Items.DIAMOND), new ItemStack(Items.GOLD_INGOT), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COAL), new ItemStack(Items.COAL), new ItemStack(Items.COAL), new ItemStack(Items.COAL), new ItemStack(Items.COAL),  new ItemStack(ModItems.itemCircuit)));
    }
}
