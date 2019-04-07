package koopamillion.mymod.plugins;

import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class PluginTwilightForest extends BasePlugin {
    public static final String MOD_ID = "twilightforest";
    public static final String MOD_NAME = "Twilight Forest";

    public PluginTwilightForest() {
        super(MOD_ID, MOD_NAME);
    }

    public void init() {
        ItemStack venison = getItemStack("raw_venison",1);

        if (!venison.isEmpty()) {
            PlushyStuff.addCustomLoot(new ItemStack(Items.PORKCHOP), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "wild_boar"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.MUTTON), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "bighorn_sheep"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.RABBIT_HIDE), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "bunny"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.LEATHER), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "deer"));
            PlushyStuff.addCustomLoot(venison, 12, getPlushyModEntitiesCanonicalName(MOD_ID, "deer"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.GLOWSTONE_DUST), 6, getPlushyModEntitiesCanonicalName(MOD_ID, "firefly"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.FEATHER), 24, getPlushyModEntitiesCanonicalName(MOD_ID, "penguin"));
            PlushyStuff.addCustomLoot(getItemStack("raven_feather"), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "raven"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.FEATHER), 22, getPlushyModEntitiesCanonicalName(MOD_ID, "tiny_bird"));

            PlushyStuff.addCustomLoot(getItemStack("giant_sword"), 4, getPlushyModEntitiesCanonicalName(MOD_ID, "armored_giant"));
            PlushyStuff.addCustomLoot(getItemStack("armor_shard"), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "blockchain_goblin"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.PAPER), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "death_tome"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.BOOK), 4, getPlushyModEntitiesCanonicalName(MOD_ID, "death_tome"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.GUNPOWDER), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "fire_beetle"));
            PlushyStuff.addCustomLoot(getItemStack("giant_pickaxe"), 4, getPlushyModEntitiesCanonicalName(MOD_ID, "giant_miner"));
            PlushyStuff.addCustomLoot(getItemStack("armor_shard"), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "goblin_knight_lower"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.STRING), 15, getPlushyModEntitiesCanonicalName(MOD_ID, "hedge_spider"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.SPIDER_EYE), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "hedge_spider"));
            PlushyStuff.addCustomLoot(getItemStack("armor_shard"), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "helmet_crab"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.FISH), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "helmet_crab"));

            PlushyStuff.addCustomLoot(new ItemStack(Items.SNOWBALL), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "ice_crystal"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.STRING), 20, getPlushyModEntitiesCanonicalName(MOD_ID, "king_spider"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.SPIDER_EYE), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "king_spider"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.WHEAT), 15, getPlushyModEntitiesCanonicalName(MOD_ID, "kobold"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.GOLD_NUGGET), 6, getPlushyModEntitiesCanonicalName(MOD_ID, "kobold"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.GHAST_TEAR), 2, getPlushyModEntitiesCanonicalName(MOD_ID, "mini_ghast"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.GUNPOWDER), 6, getPlushyModEntitiesCanonicalName(MOD_ID, "mini_ghast"));
            PlushyStuff.addCustomLoot(getItemStack("raw_meef"), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "minotaur"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.COAL), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "redcap"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.COAL), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "redcap_sapper"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.BONE), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "skeleton_druid"));
            PlushyStuff.addCustomLoot(getItemStack("torchberries"), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "skeleton_druid"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.SLIME_BALL), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "slime_beetle"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.SNOWBALL), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "snow_guardian"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.SNOWBALL), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "stable_ice_core"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.STRING), 15, getPlushyModEntitiesCanonicalName(MOD_ID, "swarm_spider"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.SPIDER_EYE), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "swarm_spider"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.STRING), 15, getPlushyModEntitiesCanonicalName(MOD_ID, "tower_broodling"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.SPIDER_EYE), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "tower_broodling"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.GHAST_TEAR), 4, getPlushyModEntitiesCanonicalName(MOD_ID, "tower_ghast"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.GUNPOWDER), 8, getPlushyModEntitiesCanonicalName(MOD_ID, "tower_ghast"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.IRON_INGOT), 4, getPlushyModEntitiesCanonicalName(MOD_ID, "tower_golem"));
            PlushyStuff.addCustomLoot(getItemStack("tower_wood"), 12, getPlushyModEntitiesCanonicalName(MOD_ID, "tower_golem"));
            PlushyStuff.addCustomLoot(getItemStack("borer_essence"), 10, getPlushyModEntitiesCanonicalName(MOD_ID, "tower_termite"));
            PlushyStuff.addCustomLoot(getItemStack("magic_beans"), 10, getPlushyModEntitiesCanonicalName(MOD_ID, "troll"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.SNOWBALL), 13, getPlushyModEntitiesCanonicalName(MOD_ID, "unstable_ice_core"));
            PlushyStuff.addCustomLoot(getItemStack("arctic_fur"), 10, getPlushyModEntitiesCanonicalName(MOD_ID, "winter_wolf"));
            PlushyStuff.addCustomLoot(new ItemStack(Items.GLOWSTONE_DUST), 6, getPlushyModEntitiesCanonicalName(MOD_ID, "wraith"));
            PlushyStuff.addCustomLoot(getItemStack("arctic_fur"), 10, getPlushyModEntitiesCanonicalName(MOD_ID, "yeti"));

        }

    }

}