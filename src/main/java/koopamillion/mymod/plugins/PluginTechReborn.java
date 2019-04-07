package koopamillion.mymod.plugins;

import koopamillion.mymod.crafting.CentrifugeManager;
import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class PluginTechReborn extends BasePlugin {
    public static final String MOD_ID = "techreborn";
    public static final String MOD_NAME = "Tech Reborn";

    public PluginTechReborn() {
        super(MOD_ID, MOD_NAME);
    }

    public void init() {
        ItemStack sap = getItemStack("part",1,31);
        ItemStack rubber = getItemStack("part",1,32);
        ItemStack rubberlog = getItemStack("rubber_log",16);
        ItemStack pyrite = getItemStack("dust",16,39);
        ItemStack ruby = getItemStack("dust",3, 43);
        ItemStack smallpyrite = getItemStack("smalldust",1,39);
        ItemStack smallcalcite = getItemStack("smalldust",1, 8);
        ItemStack smallsodalite = getItemStack("smalldust",2, 48);
        ItemStack lazurite = getItemStack("dust",3, 28);

        ItemStack garnet = getItemStack("dust",16,41);
        ItemStack pyrope = getItemStack("dust",3, 40);
        ItemStack almandine = getItemStack("dust",5, 0);
        ItemStack spess = getItemStack("dust",8, 49);

        ItemStack ygarnet = getItemStack("dust",16,58);
        ItemStack ypyrope = getItemStack("dust",5, 2);
        ItemStack yalmandine = getItemStack("dust",8, 25);
        ItemStack yspess = getItemStack("dust",3, 56);

        ItemStack marble = getItemStack("dust",8, 32);
        ItemStack mag = getItemStack("dust",1, 30);
        ItemStack cal = getItemStack("dust",7, 8);

        ItemStack cal2 = getItemStack("dust",3, 8);
        ItemStack bas = getItemStack("dust",16, 4);
        ItemStack per = getItemStack("dust",1, 36);
        ItemStack dar = getItemStack("dust",4, 15);
        ItemStack fl = getItemStack("dust",8, 22);

        final ItemStack e = ItemStack.EMPTY;

        if (!sap.isEmpty()) {
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(sap.getItem(), 4, sap.getMetadata())), new ItemStack(rubber.getItem(), 14, rubber.getMetadata()),
                    e, e, e, e, e, e, e,e,0,0,0,0,0,0,0,0,0,500);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(rubberlog), new ItemStack(sap.getItem(), 8, sap.getMetadata()),
                    e, e, e, e, e, e, e,e,0,0,0,0,0,0,0,0,0,1000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(Items.REDSTONE, 32)), pyrite, ruby,
                    e, e, e, e, e, e,e,0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(new ItemStack(Items.DYE, 4,4)), lazurite, smallpyrite,
                    smallcalcite, smallsodalite, e, e, e, e,e,0,0,0,0,0,0,0,0,0,1000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(garnet), pyrope, almandine,
                    spess, e, e, e, e, e,e,0,0,0,0,0,0,0,0,0,2000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(garnet), pyrope, almandine,
                    spess, e, e, e, e, e,e,0,0,0,0,0,0,0,0,0,1000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(ygarnet), ypyrope, yalmandine,
                    yspess, e, e, e, e, e,e,0,0,0,0,0,0,0,0,0,1000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(marble), mag, cal,
                    e, e, e, e, e, e,e,0,0,0,0,0,0,0,0,0,1000);
            CentrifugeManager.addRecipe(Ingredient.fromStacks(bas), cal2, per,
                    dar, fl, e, e, e, e,e,0,0,0,0,0,0,0,0,0,2000);
        }

    }

}