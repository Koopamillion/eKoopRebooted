package koopamillion.mymod.jei;

import koopamillion.mymod.crafting.CentrifugeRecipe;
import koopamillion.mymod.crafting.SolderRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.Arrays;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CentrifugeRecipeWrapper implements IRecipeWrapper {

    private final List<List<ItemStack>> inputs;
    private final List<ItemStack> outputs;
    public final CentrifugeRecipe theRecipe;

    public CentrifugeRecipeWrapper(CentrifugeRecipe recipe) {
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();
        this.theRecipe = recipe;

        inputs.add(getListForElement(recipe.getInput1().getMatchingStacks().clone()));

        outputs.add(recipe.getOutput1());
        outputs.add(recipe.getOutput2());
        outputs.add(recipe.getOutput3());
        outputs.add(recipe.getOutput4());
        outputs.add(recipe.getOutput5());
        outputs.add(recipe.getOutput6());
        outputs.add(recipe.getOutput7());
        outputs.add(recipe.getOutput8());
        outputs.add(recipe.getOutput9());








    }

    public int getChance(int i){
        i = i + 1;
        int j = 0;
        switch(i){
            case 1:
                j = this.theRecipe.getChance1();
                break;
            case 2:
                j = this.theRecipe.getChance2();
                break;
            case 3:
                j = this.theRecipe.getChance3();
                break;
            case 4:
                j = this.theRecipe.getChance4();
                break;
            case 5:
                j = this.theRecipe.getChance5();
                break;
            case 6:
                j = this.theRecipe.getChance6();
                break;
            case 7:
                j =  this.theRecipe.getChance7();
                break;
            case 8:
                j = this.theRecipe.getChance8();
                break;
            case 9:
                j =  this.theRecipe.getChance9();
                break;
        }
        return j;
    }

    @Override
    public void getIngredients(@Nonnull IIngredients ingredients) {
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

    }



    @Override
    public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(this.theRecipe.getRPM() + "RPM", 122,17,0xFFFFFF,false);
        minecraft.fontRenderer.drawString("Required", 122,25,0xFFFFFF,false);
        minecraft.fontRenderer.drawString("Below", 9,31,0xFFFFFF,false);
        minecraft.fontRenderer.drawString("Centrifuge", 9,39,0xFFFFFF,false);
        for(int i = 0; i < 9; i++) {

                switch(i){
                    case 0:
                        if(getChance(i) == 0){
                            minecraft.fontRenderer.drawString( "100%", 9 + i * 18,78,0x727272,false);
                        }   else{
                            minecraft.fontRenderer.drawString( getChance(i) + "%", 9 + i * 18,78,0x727272,false);
                        }
                        break;
                    case 1:
                        if(getChance(i) == 0){
                            minecraft.fontRenderer.drawString( "100%", 9 + i * 18,48,0x727272,false);
                        }   else{
                            minecraft.fontRenderer.drawString( getChance(i) + "%", 9 + i * 18,48,0x727272,false);
                        }
                        break;
                    case 2:
                        if(getChance(i) == 0){
                            minecraft.fontRenderer.drawString( "100%", 9 + i * 18,78,0x727272,false);
                        }   else{
                            minecraft.fontRenderer.drawString( getChance(i) + "%", 9 + i * 18,78,0x727272,false);
                        }
                        break;
                    case 3:
                        if(getChance(i) == 0){
                            minecraft.fontRenderer.drawString( "100%", 9 + i * 18,48,0x727272,false);
                        }   else{
                            minecraft.fontRenderer.drawString( getChance(i) + "%", 9 + i * 18,48,0x727272,false);
                        }
                        break;
                    case 4:
                        if(getChance(i) == 0){
                            minecraft.fontRenderer.drawString( "100%", 9 + i * 18,78,0x727272,false);
                        }   else{
                            minecraft.fontRenderer.drawString( getChance(i) + "%", 9 + i * 18,78,0x727272,false);
                        }
                        break;
                    case 5:
                        if(getChance(i) == 0){
                            minecraft.fontRenderer.drawString( "100%", 9 + i * 18,48,0x727272,false);
                        }   else{
                            minecraft.fontRenderer.drawString( getChance(i) + "%", 9 + i * 18,48,0x727272,false);
                        }
                        break;
                    case 6:
                        if(getChance(i) == 0){
                            minecraft.fontRenderer.drawString( "100%", 9 + i * 18,78,0x727272,false);
                        }   else{
                            minecraft.fontRenderer.drawString( getChance(i) + "%", 9 + i * 18,78,0x727272,false);
                        }
                        break;
                    case 7:
                        if(getChance(i) == 0){
                            minecraft.fontRenderer.drawString( "100%", 9 + i * 18,48,0x727272,false);
                        }   else{
                            minecraft.fontRenderer.drawString( getChance(i) + "%", 9 + i * 18,48,0x727272,false);
                        }
                        break;
                    case 8:
                        if(getChance(i) == 0){
                            minecraft.fontRenderer.drawString( "100%", 9 + i * 18,78,0x727272,false);
                        }   else{
                            minecraft.fontRenderer.drawString( getChance(i) + "%", 9 + i * 18,78,0x727272,false);
                        }
                        break;

                }




        }

    }



    public static List<ItemStack> getListForElement(ItemStack[] a){
        return Arrays.asList(a);
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return Collections.emptyList();
    }
}