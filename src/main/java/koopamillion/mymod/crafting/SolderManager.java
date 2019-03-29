package koopamillion.mymod.crafting;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.ModLiquids;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

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

    @Nullable
    public static SolderRecipe getRecipe(ItemStack input1,ItemStack input2,ItemStack input3,ItemStack input4,ItemStack inputboard, ItemStack input6, ItemStack input7, ItemStack input8, ItemStack input9, FluidStack input10, ItemStack input11) {
        for (SolderRecipe recipe : getCustomRecipeList()) {
            if (ItemStack.areItemsEqual(input1, recipe.getInput1())
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
        }

        return null;
    }

    private static void init() {
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.IRON_INGOT), new ItemStack(ModItems.siliconpure), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.REDSTONE),  new ItemStack(ModItems.itemCircuit), new FluidStack(ModLiquids.solderfluid, 100, null), new ItemStack(ModItems.itemSolder, 1)));
        customRecipeList.add(new SolderRecipe(new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS), new ItemStack(Items.REDSTONE), new ItemStack(Blocks.GLOWSTONE), new ItemStack(Items.REDSTONE), new ItemStack(Items.DIAMOND), new ItemStack(ModItems.itemCircuit), new ItemStack(Items.DIAMOND),  new ItemStack(ModItems.photon, 4), new FluidStack(ModLiquids.solderfluid, 100, null), new ItemStack(ModItems.itemSolder, 1)));
        customRecipeList.add(new SolderRecipe(new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS), new ItemStack(Blocks.GLASS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COMPASS), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE), new ItemStack(ModItems.itemCircuit), new ItemStack(Items.REDSTONE),  new ItemStack(ModItems.energy, 1), new FluidStack(ModLiquids.solderfluid, 200, null), new ItemStack(ModItems.itemSolder, 2)));
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.ENDER_PEARL), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.ENDER_EYE), new ItemStack(ModItems.itemCircuit), new ItemStack(Items.REDSTONE), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.IRON_INGOT),  new ItemStack(ModItems.ender, 2), new FluidStack(ModLiquids.enderfluid, 200, null), new ItemStack(ModItems.enderingot, 2)));
     //to do make silver solder v
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.MAGMA_CREAM), new ItemStack(ModItems.itemCircuit), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Items.IRON_INGOT), new ItemStack(Items.REDSTONE),  new ItemStack(ModItems.mobholder, 1), new FluidStack(ModLiquids.solderfluid, 500, null), new ItemStack(ModItems.itemSolder, 5)));
        customRecipeList.add(new SolderRecipe(new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.GHAST_TEAR), new ItemStack(Items.BLAZE_ROD), new ItemStack(Items.BONE), new ItemStack(Items.DIAMOND), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.FERMENTED_SPIDER_EYE), new ItemStack(Items.RABBIT_FOOT), new ItemStack(Blocks.SOUL_SAND),  new ItemStack(ModItems.lifecore, 1), new FluidStack(ModLiquids.enderfluid, 1000, null), new ItemStack(ModItems.enderingot, 10)));
    }
}
