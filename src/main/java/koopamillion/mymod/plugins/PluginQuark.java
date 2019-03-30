package koopamillion.mymod.plugins;

import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.item.ItemStack;

public class PluginQuark  extends BasePlugin {
    public static final String MOD_ID = "quark";
    public static final String MOD_NAME = "quark";

    public PluginQuark() {
        super(MOD_ID, MOD_NAME);
    }

    public void init() {
        ItemStack ash = getItemStack("black_ash");

        if (!ash.isEmpty()) {
            PlushyStuff.addCustomLoot(ash, 12, EntityWitherSkeleton.class.getCanonicalName());
            PlushyStuff.addCustomLoot(ash, 12, EntityWither.class.getCanonicalName());
        }

    }

}