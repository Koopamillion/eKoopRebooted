package koopamillion.mymod.plugins;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class PluginDraconicEvolution extends BasePlugin {
    public static final String MOD_ID = "draconicevolution";
    public static final String MOD_NAME = "Draconic Evolution";
    public PluginDraconicEvolution(){super(MOD_ID, MOD_NAME);}

    public void init(){
        ItemStack dragonHeart = getItemStack("dragon_heart", 1);
        ItemStack chaosFragment = getItemStack("chaos_shard", 1,2);
        ItemStack chaosFragmentTiny = getItemStack("chaos_shard", 1,3);
        if(!dragonHeart.isEmpty()){
            PlushyStuff.addCustomLoot(new ItemStack(ModItems.dragonheartnugget), 1, EntityDragon.class.getCanonicalName());
        }
        if(!chaosFragment.isEmpty()){
            PlushyStuff.addCustomLoot(chaosFragment, 1, getPlushyModEntitiesCanonicalName(MOD_ID, "chaosguardian"));
            PlushyStuff.addCustomLoot(chaosFragmentTiny, 3, getPlushyModEntitiesCanonicalName(MOD_ID, "chaosguardian"));
            PlushyStuff.addCustomLoot(new ItemStack(ModItems.dragonheartnugget), 1, getPlushyModEntitiesCanonicalName(MOD_ID, "chaosguardian"));
        }

    }

}
