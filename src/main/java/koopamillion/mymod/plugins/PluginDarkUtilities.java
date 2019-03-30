package koopamillion.mymod.plugins;

import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.item.ItemStack;

public class PluginDarkUtilities extends BasePlugin {
    public static final String MOD_ID = "darkutils";
    public static final String MOD_NAME = "Dark Utilities";

    public PluginDarkUtilities() {
        super(MOD_ID, MOD_NAME);
    }

    public void init() {
        ItemStack shulker_pearl = getItemStack("shulker_pearl");
        ItemStack dust = getItemStack("material", 1, 0);
        if (!shulker_pearl.isEmpty()) {
            PlushyStuff.addCustomLoot(shulker_pearl, 8, EntityShulker.class.getCanonicalName());
        }
        if (!dust.isEmpty()) {
            PlushyStuff.addCustomLoot(dust, 5, EntityWitherSkeleton.class.getCanonicalName());
        }
    }

}