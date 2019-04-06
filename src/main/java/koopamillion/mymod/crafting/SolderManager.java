package koopamillion.mymod.crafting;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.ModLiquids;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
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

  /*  @Nullable
    public static SolderRecipe getRecipe(ItemStack input1,ItemStack input2,ItemStack input3,ItemStack input4,ItemStack inputboard, ItemStack input6, ItemStack input7, ItemStack input8, ItemStack input9, FluidStack input10, ItemStack input11) {
        for (SolderRecipe recipe : getCustomRecipeList()) {
           /* if (ItemStack.areItemsEqual(input1, recipe.getInput1())
                    &&ItemStack.areItemsEqual(input2, recipe.getInput2())
                    &&ItemStack.areItemsEqual(input3, recipe.getInput3())
                    &&ItemStack.areItemsEqual(input4, recipe.getInput4())
                    &&ItemStack.areItemsEqual(inputboard, recipe.getInputboard())
                    &&ItemStack.areItemsEqual(input6, recipe.getInput6())
                    &&ItemStack.areItemsEqual(input7, recipe.getInput7())
                    &&ItemStack.areItemsEqual(input8, recipe.getInput8())
                    &&ItemStack.areItemsEqual(input9, recipe.getInput9())
            &&FluidStack.areFluidStackTagsEqual(input10, recipe.getInput10())) {
                return recipe;
            }
           if(!(input1.isEmpty()) &&!(input2.isEmpty()) &&!(input3.isEmpty()) &&!(input4.isEmpty()) &&!(inputboard.isEmpty()) &&!(input6.isEmpty()) &&!(input7.isEmpty()) &&!(input8.isEmpty()) &&!(input9.isEmpty())) {

               System.out.println(OreDictionary.getOreName(OreDictionary.getOreIDs(input4)));
               new OreIngredient("ingotCopper")

               if (OreDictionary.getOreIDs(input1).equals(OreDictionary.getOreIDs(recipe.getInput1()))
                       && OreDictionary.getOreIDs(input2).equals(OreDictionary.getOreIDs(recipe.getInput2()))
                       && OreDictionary.getOreIDs(input3).equals(OreDictionary.getOreIDs(recipe.getInput3()))
                       && OreDictionary.getOreIDs(input4).equals(OreDictionary.getOreIDs(recipe.getInput4()))
                       && OreDictionary.getOreIDs(inputboard).equals(OreDictionary.getOreIDs(recipe.getInputboard()))
                       && OreDictionary.getOreIDs(input6).equals(OreDictionary.getOreIDs(recipe.getInput6()))
                       && OreDictionary.getOreIDs(input7).equals(OreDictionary.getOreIDs(recipe.getInput7()))
                       && OreDictionary.getOreIDs(input8).equals(OreDictionary.getOreIDs(recipe.getInput8()))
                       && OreDictionary.getOreIDs(input9).equals(OreDictionary.getOreIDs(recipe.getInput9()))
                       && FluidStack.areFluidStackTagsEqual(input10, recipe.getInput10())
               ) {
                   return recipe;
               }
           }
        }

        return null;
    }*/

    @Nullable
    public static SolderRecipe getRecipe(ItemStack input1,ItemStack input2,ItemStack input3,ItemStack input4,ItemStack inputboard, ItemStack input6, ItemStack input7, ItemStack input8, ItemStack input9, FluidStack input10) {
        for (SolderRecipe recipe : getCustomRecipeList()) {


                if(recipe.getInput1().apply(input1)&&
                        recipe.getInput2().apply(input2)&&
                        recipe.getInput3().apply(input3)&&
                        recipe.getInput4().apply(input4)&&
                        recipe.getInputboard().apply(inputboard)&&
                        recipe.getInput6().apply(input6)&&
                        recipe.getInput7().apply(input7)&&
                        recipe.getInput8().apply(input8)&&
                        recipe.getInput9().apply(input9)&&

                        FluidStack.areFluidStackTagsEqual(input10, recipe.getInput10())){
                    return recipe;
            }



        }

        return null;
    }

   /* private static void init() {
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.siliconpure), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE),  new ItemStack(ModItems.itemCircuit), new FluidStack(ModLiquids.solderfluid, 100, null), new ItemStack(ModItems.itemSolder, 1)));
        customRecipeList.add(new SolderRecipe(new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS), new ItemStack(Items.REDSTONE), new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.DIAMOND), new ItemStack(ModItems.itemCircuit), new ItemStack(Items.DIAMOND),  new ItemStack(ModItems.photon, 4), new FluidStack(ModLiquids.solderfluid, 100, null), new ItemStack(ModItems.itemSolder, 1)));
        customRecipeList.add(new SolderRecipe(new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COMPASS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE), new ItemStack(ModItems.itemCircuit), new ItemStack(Items.REDSTONE),  new ItemStack(ModItems.energy, 1), new FluidStack(ModLiquids.solderfluid, 200, null), new ItemStack(ModItems.itemSolder, 2)));
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.ENDER_PEARL), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.ENDER_EYE), new ItemStack(ModItems.itemCircuit), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT),  new ItemStack(ModItems.ender, 2), new FluidStack(ModLiquids.enderfluid, 200, null), new ItemStack(ModItems.enderingot, 2)));
     //to do make silver solder v
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.MAGMA_CREAM), new ItemStack(ModItems.itemCircuit), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE),  new ItemStack(ModItems.mobholder, 1), new FluidStack(ModLiquids.solderfluid, 500, null), new ItemStack(ModItems.itemSolder, 5)));
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BONE), new ItemStack(Items.DIAMOND), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.RABBIT_FOOT), new ItemStack(Blocks.SOUL_SAND),  new ItemStack(ModItems.lifecore, 1), new FluidStack(ModLiquids.enderfluid, 1000, null), new ItemStack(ModItems.enderingot, 10)));
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.ENDER_PEARL), new ItemStack(ModItems.itemCircuit), new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.IRON_INGOT), new ItemStack(Blocks.TNT), new ItemStack(Items.IRON_INGOT),  new ItemStack(ModItems.mobram, 1), new FluidStack(ModLiquids.enderfluid, 100, null), new ItemStack(ModItems.enderingot, 1)));
    }*/

    private static void init() {
        customRecipeList.add(new SolderRecipe(
                new OreIngredient("dustRedstone"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("ingotIron"),
                new OreIngredient("waferSilicon"),
                new OreIngredient("ingotIron"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("dustRedstone"),
                new ItemStack(ModItems.itemCircuit),
                new FluidStack(ModLiquids.solderfluid, 100, null),
                new ItemStack(ModItems.itemSolder, 1)));
        customRecipeList.add(new SolderRecipe(
                new OreIngredient("blockGlass"),
                new OreIngredient("blockGlass"),
                new OreIngredient("blockGlass"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("dustGlowstone"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("gemDiamond"),
                new OreIngredient("circuitBasic"),
                new OreIngredient("gemDiamond"),
                new ItemStack(ModItems.photon, 4),
                new FluidStack(ModLiquids.solderfluid, 100, null),
                new ItemStack(ModItems.itemSolder, 1)));
        customRecipeList.add(new SolderRecipe(
                new OreIngredient("blockGlass"),
                new OreIngredient("blockGlass"),
                new OreIngredient("blockGlass"),
                new OreIngredient("ingotIron"),
                Ingredient.fromItem(Items.COMPASS),
                new OreIngredient("ingotIron"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("circuitBasic"),
                new OreIngredient("dustRedstone"),
                new ItemStack(ModItems.energy),
                new FluidStack(ModLiquids.solderfluid, 200, null),
                new ItemStack(ModItems.itemSolder, 2)));
        customRecipeList.add(new SolderRecipe(
                new OreIngredient("enderpearl"),
                Ingredient.EMPTY,
                Ingredient.EMPTY,
                Ingredient.fromItem(Items.ENDER_EYE),
                new OreIngredient("circuitBasic"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("dustGlowstone"),
                new OreIngredient("ingotIron"),
                new OreIngredient("ingotIron"),
                new ItemStack(ModItems.ender, 2),
                new FluidStack(ModLiquids.enderfluid, 200, null),
                new ItemStack(ModItems.enderingot, 2)));
        customRecipeList.add(new SolderRecipe(
                new OreIngredient("enderpearl"),
                Ingredient.fromItem(Items.GHAST_TEAR),
                new OreIngredient("enderpearl"),
                Ingredient.fromItem(Items.MAGMA_CREAM),
                new OreIngredient("circuitBasic"),
                Ingredient.fromItem(Items.BLAZE_POWDER),
                new OreIngredient("dustGlowstone"),
                new OreIngredient("ingotIron"),
                new OreIngredient("dustRedstone"),
                new ItemStack(ModItems.mobholder),
                new FluidStack(ModLiquids.solderfluid, 500, null),
                new ItemStack(ModItems.itemSolder, 5)));
        customRecipeList.add(new SolderRecipe(
                new OreIngredient("enderpearl"),
                Ingredient.fromItem(Items.GHAST_TEAR),
                Ingredient.fromItem(Items.BLAZE_ROD),
                new OreIngredient("bone"),
                new OreIngredient("gemDiamond"),
                Ingredient.fromItem(Items.ROTTEN_FLESH),
                Ingredient.fromItem(Items.FERMENTED_SPIDER_EYE),
                Ingredient.fromItem(Items.RABBIT_FOOT),
                Ingredient.fromStacks(new ItemStack(Blocks.SOUL_SAND)),
                new ItemStack(ModItems.lifecore),
                new FluidStack(ModLiquids.enderfluid, 1000, null),
                new ItemStack(ModItems.enderingot, 10)));
        customRecipeList.add(new SolderRecipe(
                new OreIngredient("ingotIron"),
                Ingredient.fromItem(Items.GHAST_TEAR),
                new OreIngredient("ingotIron"),
                new OreIngredient("enderpearl"),
                new OreIngredient("circuitBasic"),
                new OreIngredient("enderpearl"),
                new OreIngredient("ingotIron"),
                Ingredient.fromStacks(new ItemStack(Blocks.TNT)),
                new OreIngredient("ingotIron"),
                new ItemStack(ModItems.mobram),
                new FluidStack(ModLiquids.enderfluid, 100, null),
                new ItemStack(ModItems.enderingot, 1)));
        customRecipeList.add(new SolderRecipe(
                new OreIngredient("dustGlowstone"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("dustGlowstone"),
                new OreIngredient("ingotSilver"),
                new OreIngredient("circuitBasic"),
                new OreIngredient("ingotSilver"),
                new OreIngredient("dustGlowstone"),
                new OreIngredient("dustRedstone"),
                new OreIngredient("dustGlowstone"),
                new ItemStack(ModItems.advancedcircuit),
                new FluidStack(ModLiquids.silversolder, 100, null),
                new ItemStack(ModItems.silversolderingot, 1)));
   }

}
