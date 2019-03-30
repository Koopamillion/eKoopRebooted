package koopamillion.mymod.plugins;

import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.item.ItemStack;

public class PluginTinkersConstruct  extends BasePlugin {
    public static final String MOD_ID = "tconstruct";
    public static final String MOD_NAME = "Tinkers' Construct";

    public PluginTinkersConstruct() {
        super(MOD_ID, MOD_NAME);
    }

    public void init() {
        ItemStack bone = getItemStack("materials",1,17);

        if (!bone.isEmpty()) {
            PlushyStuff.addCustomLoot(bone, 3, EntityWitherSkeleton.class.getCanonicalName());
        }

    }

}