package koopamillion.mymod.plugins;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.tools.PlushyStuff;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PluginMysticalAgriculture extends BasePlugin {
    public static final String MOD_ID = "mysticalagriculture";
    public static final String MOD_NAME = "Mystical Agriculture";

    public PluginMysticalAgriculture() {
        super(MOD_ID, MOD_NAME);
    }

    public void init() {
        ItemStack chunkstack = getItemStack("chunk", 1, 5);
        ItemStack essence = getItemStack("crafting", 1, 0);
        ItemStack supessence = getItemStack("crafting", 1, 4);
        Item chunk = chunkstack.getItem();
        if (!chunkstack.isEmpty()) {
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 6), 1, EntityZombie.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 7), 1, EntityPig.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 8), 1, EntityChicken.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 9), 1, EntityCow.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 10), 1, EntitySheep.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 11), 1, EntitySlime.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 12), 1, EntitySkeleton.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 13), 1, EntityCreeper.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 14), 1, EntitySpider.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 15), 1, EntityRabbit.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 16), 1, EntityGuardian.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 17), 1, EntityBlaze.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 18), 1, EntityGhast.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 19), 1, EntityEnderman.class.getCanonicalName());
            PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 20), 1, EntityWitherSkeleton.class.getCanonicalName());
            if(!getPlushyModEntitiesCanonicalName("thermalfoundation", "blizz").isEmpty()){
                PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 21), 1, getPlushyModEntitiesCanonicalName("thermalfoundation", "blizz"));
            }
            if(!getPlushyModEntitiesCanonicalName("thermalfoundation", "blitz").isEmpty()){
                PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 22), 1, getPlushyModEntitiesCanonicalName("thermalfoundation", "blitz"));
            }
            if(!getPlushyModEntitiesCanonicalName("thermalfoundation", "basalz").isEmpty()){
                PlushyStuff.addCustomLoot(new ItemStack(chunk, 1, 23), 1, getPlushyModEntitiesCanonicalName("thermalfoundation", "basalz"));
            }
            PlushyStuff.addCustomLoot(supessence, 2,  EntityWither.class.getCanonicalName());
            PlushyStuff.addCustomLoot(supessence, 3,  EntityDragon.class.getCanonicalName());
            for(int i = 0; i < ForgeRegistries.ENTITIES.getEntries().size(); i++){
                if(EntityMob.class.isAssignableFrom(ForgeRegistries.ENTITIES.getValues().get(i).getEntityClass()) && !(EntityDragon.class.isAssignableFrom(ForgeRegistries.ENTITIES.getValues().get(i).getEntityClass())) && !(EntityWither.class.isAssignableFrom(ForgeRegistries.ENTITIES.getValues().get(i).getEntityClass()))){
                    PlushyStuff.addCustomLoot(chunkstack, 1, ForgeRegistries.ENTITIES.getValues().get(i).getEntityClass().getCanonicalName());
                    PlushyStuff.addCustomLoot(essence, 7, ForgeRegistries.ENTITIES.getValues().get(i).getEntityClass().getCanonicalName());
                }
                if(EntityAnimal.class.isAssignableFrom(ForgeRegistries.ENTITIES.getValues().get(i).getEntityClass()) ){
                    PlushyStuff.addCustomLoot(essence, 5, ForgeRegistries.ENTITIES.getValues().get(i).getEntityClass().getCanonicalName());
                }



            }
        }

    }
}
