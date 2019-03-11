package koopamillion.mymod.jei;

import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.crafting.AcidManager;
import koopamillion.mymod.crafting.AcidRecipe;
import koopamillion.mymod.crafting.SolderManager;
import koopamillion.mymod.crafting.SolderRecipe;
import koopamillion.mymod.furnace.ContainerFurnace;
import koopamillion.mymod.furnace.GuiFurnace;
import koopamillion.mymod.furnace.TileFurnace;
import koopamillion.mymod.soldertable.ContainerSolderTable;
import koopamillion.mymod.soldertable.GuiSolderTable;
import koopamillion.mymod.soldertable.TileSolderTable;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import javax.annotation.Nonnull;
import java.util.Collections;

@JEIPlugin
public class JeiPlugin implements IModPlugin {

    public static final String FASTFURNACE_ID = "mymod.furnace";
    public static final String SOLDER_ID = "mymod.solder";
    public static final String ACID_ID = "mymod.acidbath";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registerFastFurnaceHandling(registry);
        registerSolderHandling(registry);
        registerAcidHandling(registry);
        registry.addRecipeClickArea(GuiSolderTable.class, 121, 43, 15, 16, SOLDER_ID);
        registry.addRecipeClickArea(GuiFurnace.class, 79, 25, 22, 15, VanillaRecipeCategoryUid.SMELTING);
    }

    private void registerSolderHandling(@Nonnull IModRegistry registry) {
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.blockSolder), SOLDER_ID);
        registry.addRecipes(SolderManager.getCustomRecipeList(), SOLDER_ID);
        registry.handleRecipes(SolderRecipe.class, SolderRecipeWrapper::new, SOLDER_ID);

        transferRegistry.addRecipeTransferHandler(ContainerSolderTable.class, SOLDER_ID,
                0, TileSolderTable.INPUT_SLOTS, TileSolderTable.INPUT_SLOTS + TileSolderTable.OUTPUT_SLOTS, 36);
    }

    private void registerAcidHandling(@Nonnull IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.blockAcidbath), ACID_ID);
        registry.addRecipes(AcidManager.getCustomRecipeList(), ACID_ID);
        registry.handleRecipes(AcidRecipe.class, AcidRecipeWrapper::new, ACID_ID);

    }

    private void registerFastFurnaceHandling(@Nonnull IModRegistry registry) {
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.blockFurnace), FASTFURNACE_ID, VanillaRecipeCategoryUid.SMELTING);

    //use the + to transfer
        IRecipeTransferRegistry transferRegistry = registry.getRecipeTransferRegistry();
        transferRegistry.addRecipeTransferHandler(ContainerFurnace.class, VanillaRecipeCategoryUid.SMELTING,
                0, TileFurnace.INPUT_SLOTS, TileFurnace.INPUT_SLOTS + TileFurnace.OUTPUT_SLOTS, 36);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IJeiHelpers helpers = registry.getJeiHelpers();
        IGuiHelper guiHelper = helpers.getGuiHelper();

        registry.addRecipeCategories(new SolderRecipeCategory(guiHelper));
        registry.addRecipeCategories(new AcidRecipeCategory(guiHelper));
    }

}
