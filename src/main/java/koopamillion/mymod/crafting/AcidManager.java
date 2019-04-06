package koopamillion.mymod.crafting;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.ModLiquids;
import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.oredict.OreIngredient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class AcidManager {

    private static boolean isInit = false;
    private static List<AcidRecipe> customRecipeList = new ArrayList<>();

    public static List<AcidRecipe> getCustomRecipeList() {
        if (!isInit) {
            init();
            isInit = true;
        }
        return customRecipeList;
    }

    @Nullable
    public static AcidRecipe getRecipe(ItemStack input1, FluidStack input2, Boolean bool, Boolean bool2, ItemStack input3, ItemStack input4) {
        for (AcidRecipe recipe : getCustomRecipeList()) {
            if (recipe.getInput1().apply(input1)
                    &&FluidStack.areFluidStackTagsEqual(input2, recipe.getInput2())
                &&(bool==recipe.getBool())&&(bool2==recipe.getBool2())) {

                return recipe;
            }

        }
        return null;
    }




    private static void init() {
        customRecipeList.add(new AcidRecipe(
                Ingredient.fromItem(ModItems.puresand),
                new ItemStack(ModItems.siliconraw, 1, 0),
                new FluidStack(FluidRegistry.LAVA, 100, null),
                false, false, null, null, null));
        customRecipeList.add(new AcidRecipe(
                new OreIngredient("sand"),
                new ItemStack(ModItems.puresand, 1, 0),
                new FluidStack(ModLiquids.hcl, 100, null),
                true, false, null, new ItemStack(Blocks.MAGMA), null));
        customRecipeList.add(new AcidRecipe(
                new OreIngredient("itemSalt"),
                null,
                new FluidStack(FluidRegistry.WATER, 1000, null),
                false, true,  new FluidStack(ModLiquids.hcl, 1000, null ), null, new ItemStack(Blocks.REDSTONE_BLOCK)));
        customRecipeList.add(new AcidRecipe(
                new OreIngredient("cobblestone"),
                new ItemStack(ModItems.trace, 1, 0),
                new FluidStack(ModLiquids.hcl, 20, null),
                false, false, null, null, null));
        customRecipeList.add(new AcidRecipe(
                Ingredient.fromItem(ModItems.dirty),
                new ItemStack(ModItems.clean, 1, 0),
                new FluidStack(FluidRegistry.WATER, 250, null),
                false, false, null, null, null));
    }
}
