package koopamillion.mymod.plugins;

import koopamillion.mymod.crafting.CentrifugeManager;
import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class PluginThermalExpansion extends BasePlugin {
    public static final String MOD_ID = "thermalfoundation";
    public static final String MOD_NAME = "Thermal Foundation";
    public PluginThermalExpansion(){
        super(MOD_ID, MOD_NAME);
    }

    public void init(){
        ItemStack rodBlizz = getItemStack("material", 1, 2048);
        ItemStack rodBasalz = getItemStack("material", 1, 2052);
        ItemStack rodBlitz = getItemStack("material", 1, 2050);
        ItemStack powderBlizz = getItemStack("material", 1, 2049);
        ItemStack powderBasalz = getItemStack("material", 1, 2053);
        ItemStack powderBlitz = getItemStack("material", 1, 2051);
        ItemStack dustSulfur = getItemStack("material", 1, 771);

        ItemStack electrum = getItemStack("material", 1, 97);
        ItemStack dustgold = getItemStack("material", 1, 1);
        ItemStack dustsilver = getItemStack("material", 1, 66 );

        ItemStack invar = getItemStack("material", 1, 98);
        ItemStack nickel = getItemStack("material", 1, 69);
        ItemStack iron = getItemStack("material", 1, 0);
        ItemStack bronze = getItemStack("material", 1, 99);
        ItemStack copper = getItemStack("material", 1, 64);
        ItemStack tin = getItemStack("material", 1, 65);

        ItemStack constantan = getItemStack("material", 1, 100);

        ItemStack signalum = getItemStack("material", 1, 101 );

        ItemStack lumium = getItemStack("material", 1, 102 );

        ItemStack pyrotheum = getItemStack("material", 1, 1024);
        ItemStack cryotheum = getItemStack("material", 1, 1025);
        ItemStack aerotheum = getItemStack("material", 1, 1026);
        ItemStack petrotheum = getItemStack("material", 1, 1027);

        ItemStack niter = getItemStack("material", 1, 772);

        ItemStack enderium = getItemStack("material", 1, 103);
        ItemStack shiny = getItemStack("material", 1, 70);
        ItemStack lead = getItemStack("material", 1, 67);
        ItemStack obsidian = getItemStack("material", 1, 770);

        ItemStack coal = getItemStack("material", 1, 768);
        ItemStack steel = getItemStack("material", 1, 96);

        final ItemStack e = ItemStack.EMPTY;

        if(!rodBlizz.isEmpty()){
            PlushyStuff.addCustomLoot(rodBlizz, 6, getPlushyModEntitiesCanonicalName(MOD_ID, "blizz"));
            PlushyStuff.addCustomLoot(powderBlizz, 9, getPlushyModEntitiesCanonicalName(MOD_ID, "blizz"));
        }
        if(!rodBasalz.isEmpty()){
            PlushyStuff.addCustomLoot(rodBasalz, 6, getPlushyModEntitiesCanonicalName(MOD_ID, "basalz"));
            PlushyStuff.addCustomLoot(powderBasalz, 9, getPlushyModEntitiesCanonicalName(MOD_ID, "basalz"));
        }
        if(!rodBlitz.isEmpty()){
            PlushyStuff.addCustomLoot(rodBlitz, 6, getPlushyModEntitiesCanonicalName(MOD_ID, "blitz"));
            PlushyStuff.addCustomLoot(powderBlitz, 9, getPlushyModEntitiesCanonicalName(MOD_ID, "blitz"));
        }
        if(!dustSulfur.isEmpty()){
            PlushyStuff.addCustomLoot(dustSulfur, 5, EntityMagmaCube.class.getCanonicalName());
            PlushyStuff.addCustomLoot(dustSulfur, 5, EntityBlaze.class.getCanonicalName());
            PlushyStuff.addCustomLoot(dustSulfur, 5, EntityWitherSkeleton.class.getCanonicalName());
            PlushyStuff.addCustomLoot(dustSulfur, 5, EntityPigZombie.class.getCanonicalName());
            PlushyStuff.addCustomLoot(dustSulfur, 5, EntityCreeper.class.getCanonicalName());
        }
        if(!dustgold.isEmpty()){
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(electrum.getItem(), 2, electrum.getMetadata())), dustgold, dustsilver, e,e,e,e,e,e,e,
            0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(invar.getItem(), 3, invar.getMetadata())), new ItemStack(iron.getItem(), 2), nickel, e,e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(bronze.getItem(), 4, bronze.getMetadata())), new ItemStack(copper.getItem(), 3, copper.getMetadata()), tin, e,e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(constantan.getItem(), 2, constantan.getMetadata())), copper, nickel, e,e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(signalum.getItem(), 4, signalum.getMetadata())), new ItemStack(copper.getItem(), 3, copper.getMetadata()), dustsilver, new ItemStack(Items.REDSTONE, 10),e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,3000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(lumium.getItem(), 4, lumium.getMetadata())), new ItemStack(tin.getItem(), 3, tin.getMetadata()), dustsilver, new ItemStack(Items.GLOWSTONE_DUST, 4),e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,3000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(enderium.getItem(), 4, enderium.getMetadata())), new ItemStack(lead.getItem(), 3, lead.getMetadata()), shiny, new ItemStack(Items.ENDER_PEARL, 4),e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,4000);

            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(pyrotheum.getItem(), 2, pyrotheum.getMetadata())), new ItemStack(Items.BLAZE_POWDER, 2), dustSulfur, new ItemStack(Items.REDSTONE),e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(cryotheum.getItem(), 2, cryotheum.getMetadata())), new ItemStack(powderBlizz.getItem(), 2, powderBlizz.getMetadata()), new ItemStack(Items.SNOWBALL), new ItemStack(Items.REDSTONE),e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(aerotheum.getItem(), 2, aerotheum.getMetadata())), new ItemStack(powderBlitz.getItem(), 2,powderBlitz.getMetadata()), niter, new ItemStack(Items.REDSTONE),e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(petrotheum.getItem(), 2, petrotheum.getMetadata())), new ItemStack(powderBasalz.getItem(), 2,powderBasalz.getMetadata()), obsidian, new ItemStack(Items.REDSTONE),e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(steel.getItem(), 1, steel.getMetadata())), new ItemStack(coal.getItem(), 4,coal.getMetadata()), iron, e,e,e,e,e,e,e,
                    0,0,0,0,0,0,0,0,0,10000);

        }


    }


}
