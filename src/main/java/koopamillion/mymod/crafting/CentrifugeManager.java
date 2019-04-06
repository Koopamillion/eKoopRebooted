package koopamillion.mymod.crafting;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.machineitems.ItemSilverSolderIngot;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CentrifugeManager {
    private static boolean isInit = false;
    private static List<CentrifugeRecipe> customRecipeList = new ArrayList<>();



    public static List<CentrifugeRecipe> getCustomRecipeList() {
        if (!isInit) {
            init();
            isInit = true;
        }
        return customRecipeList;
    }



    @Nullable
    public static CentrifugeRecipe getRecipe(ItemStack input1, int RPM){
        for(CentrifugeRecipe recipe: getCustomRecipeList()){
            if(recipe.getInput1().apply(input1)&& RPM >= recipe.getRPM()){
                return recipe;
            }
        }

        return null;
    }

    //use for telling the TE to speed up and for checking output slot space!

    @Nullable
    public static CentrifugeRecipe getRecipeForStatsOnly(ItemStack input1){
        for(CentrifugeRecipe recipe: getCustomRecipeList()){
            if(recipe.getInput1().apply(input1)){
                return recipe;
            }
        }

        return null;
    }

    public static void addRecipe(Ingredient ingredient, ItemStack o1, ItemStack o2, ItemStack o3, ItemStack o4, ItemStack o5, ItemStack o6, ItemStack o7, ItemStack o8, ItemStack o9,
                          int c1, int c2, int c3, int c4, int c5, int c6, int c7, int c8, int c9, int rpm){
        customRecipeList.add(new CentrifugeRecipe(
                ingredient,
                o1,
                o2,
                o3,
                o4,
                o5,
                o6,
                o7,
                o8,
                o9,
                c1,c2,c3,c4,c5,c6,c7,c8,c9,rpm
        ));
    }

    public static void init(){
        customRecipeList.add(new CentrifugeRecipe(
                Ingredient.fromItem(ModItems.mixed),
                new ItemStack(ModItems.tinytin),
                new ItemStack(ModItems.tinyaluminium),
                new ItemStack(ModItems.tinyantinomy),
                new ItemStack(ModItems.tinycopper),
                new ItemStack(ModItems.tinyiron),
                new ItemStack(ModItems.tinylead),
                new ItemStack(ModItems.tinyshinys),
                new ItemStack(ModItems.tinysilver),
                new ItemStack(ModItems.tinystone),
                12,12,10,12, 12,12,6,8,80,3000));
        customRecipeList.add(new CentrifugeRecipe(
                Ingredient.fromItem(ModItems.dustshinys),
                new ItemStack(Items.DIAMOND),
                new ItemStack(Items.EMERALD),
                new ItemStack(Items.REDSTONE),
                new ItemStack(Items.COAL),
                new ItemStack(Items.DYE, 1, 4),
               new ItemStack(Items.GOLD_NUGGET),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                1,1,12,16, 10,12,0,0,0,4000));
        customRecipeList.add(new CentrifugeRecipe(
                Ingredient.fromItem(Items.GUNPOWDER),
                new ItemStack(ModItems.dustsaltpeter),
                new ItemStack(ModItems.dustcharcoal),
                new ItemStack(ModItems.dustsulfur),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                75,15,10,0, 0,0,0,0,0,1500));
        customRecipeList.add(new CentrifugeRecipe(
                Ingredient.fromItem(Items.REEDS),
                new ItemStack(Items.SUGAR, 2),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                0,0,0,0, 0,0,0,0,0,250));
        customRecipeList.add(new CentrifugeRecipe(
                Ingredient.fromItem(Items.MAGMA_CREAM),
                new ItemStack(Items.SLIME_BALL),
                new ItemStack(Items.BLAZE_POWDER),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                0,0,0,0, 0,0,0,0,0,500));
        customRecipeList.add(new CentrifugeRecipe(
                Ingredient.fromStacks(new ItemStack(Blocks.DIRT)),
                new ItemStack(ModItems.tinystone, 9),
                new ItemStack(ModItems.tinystone, 2),
                new ItemStack(ModItems.tinyiron),
                new ItemStack(ModItems.tinytin),
                new ItemStack(ModItems.tinycopper),
                new ItemStack(ModItems.tinyshinys),
                new ItemStack(ModItems.dirty),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                0,50,3,3, 3,1,1,0,0,1500));
        customRecipeList.add(new CentrifugeRecipe(
                Ingredient.fromStacks(new ItemStack(Blocks.DIRT, 1, 1)),
                new ItemStack(ModItems.tinystone, 11),
                new ItemStack(ModItems.tinystone, 4),
                new ItemStack(ModItems.tinyiron),
                new ItemStack(ModItems.tinytin),
                new ItemStack(ModItems.tinycopper),
                new ItemStack(ModItems.tinyshinys),
                new ItemStack(ModItems.dirty),
                ItemStack.EMPTY,
                ItemStack.EMPTY,
                0,65,4,4, 4,2,1,0,0,1500));





    }

}
