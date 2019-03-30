package koopamillion.mymod.plugins;

import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PluginMysticalAgradations extends BasePlugin{
        public static final String MOD_ID = "mysticalagradditions";
        public static final String MOD_NAME = "Mystical Agradditions";

        public PluginMysticalAgradations() {
            super(MOD_ID, MOD_NAME);
        }

        public void init() {
            ItemStack stuff = getItemStack("stuff", 1, 1);
            if (!stuff.isEmpty()) {
                PlushyStuff.addCustomLoot(stuff, 1, EntityWither.class.getCanonicalName());
            }
        }

}