package koopamillion.mymod.plugins;

import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.monster.*;
import net.minecraft.item.ItemStack;

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
        if(!rodBlizz.isEmpty()){
            PlushyStuff.addCustomLoot(rodBlizz, 5, getPlushyModEntitiesCanonicalName(MOD_ID, "blizz"));
            PlushyStuff.addCustomLoot(powderBlizz, 8, getPlushyModEntitiesCanonicalName(MOD_ID, "blizz"));
        }
        if(!rodBasalz.isEmpty()){
            PlushyStuff.addCustomLoot(rodBasalz, 5, getPlushyModEntitiesCanonicalName(MOD_ID, "basalz"));
            PlushyStuff.addCustomLoot(powderBasalz, 8, getPlushyModEntitiesCanonicalName(MOD_ID, "basalz"));
        }
        if(!rodBlitz.isEmpty()){
            PlushyStuff.addCustomLoot(rodBlitz, 5, getPlushyModEntitiesCanonicalName(MOD_ID, "blitz"));
            PlushyStuff.addCustomLoot(powderBlitz, 8, getPlushyModEntitiesCanonicalName(MOD_ID, "blitz"));
        }
        if(!dustSulfur.isEmpty()){
            PlushyStuff.addCustomLoot(dustSulfur, 3, EntityMagmaCube.class.getCanonicalName());
            PlushyStuff.addCustomLoot(dustSulfur, 3, EntityBlaze.class.getCanonicalName());
            PlushyStuff.addCustomLoot(dustSulfur, 3, EntityWitherSkeleton.class.getCanonicalName());
            PlushyStuff.addCustomLoot(dustSulfur, 3, EntityPigZombie.class.getCanonicalName());
            PlushyStuff.addCustomLoot(dustSulfur, 3, EntityCreeper.class.getCanonicalName());
        }


    }


}
