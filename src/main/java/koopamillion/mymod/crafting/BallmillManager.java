package koopamillion.mymod.crafting;

import koopamillion.mymod.ModItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BallmillManager {
    private static boolean isInit = false;
    private static List<BallmillRecipe> customRecipeList = new ArrayList<>();



    public static List<BallmillRecipe> getCustomRecipeList() {
        if (!isInit) {
            init();
            isInit = true;
        }
        return customRecipeList;
    }



    @Nullable
    public static BallmillRecipe getRecipe(ItemStack input1, int RPM){
        for(BallmillRecipe recipe: getCustomRecipeList()){
            if(recipe.getInput1().apply(input1)&& RPM >= recipe.getRPM()){
                return recipe;
            }
        }

        return null;
    }

    //use for telling the TE to speed up and for checking output slot space!

    @Nullable
    public static BallmillRecipe getRecipeForStatsOnly(ItemStack input1){
        for(BallmillRecipe recipe: getCustomRecipeList()){
            if(recipe.getInput1().apply(input1)){
                return recipe;
            }
        }

        return null;
    }

    public static void addRecipe(Ingredient ingredient, ItemStack o1, ItemStack o2, ItemStack o3,
                          int c1, int c2, int c3,int rpm, int tier){
        customRecipeList.add(new BallmillRecipe(
                ingredient,
                o1,
                o2,
                o3,
                c1,c2,c3,rpm, tier
        ));
    }

    public static void init(){
        customRecipeList.add(new BallmillRecipe(
                Ingredient.fromItem(ModItems.mixed),
                new ItemStack(ModItems.tinytin),
                new ItemStack(ModItems.tinyaluminium),
                new ItemStack(ModItems.tinyantinomy),

                0,50,10,1500, 2));

    }

}
