package koopamillion.mymod.plugins;

import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.item.ItemStack;

public class PluginExtraUtilities2   extends BasePlugin {
    public static final String MOD_ID = "extrautils2";
    public static final String MOD_NAME = "Extra Utilities 2";

    public PluginExtraUtilities2() {
        super(MOD_ID, MOD_NAME);
    }

    public void init() {
        ItemStack evil = getItemStack("ingredients",1,10);

        if (!evil.isEmpty()) {
            PlushyStuff.addCustomLoot(evil, 1, EntityWitherSkeleton.class.getCanonicalName());
        }

    }

}