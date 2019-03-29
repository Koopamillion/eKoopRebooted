package koopamillion.mymod.tools;

import koopamillion.mymod.ModBlocks;
import koopamillion.mymod.ModItems;
import koopamillion.mymod.MyMod;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Mod;

import java.util.*;

public class PlushyStuff {

    public static Map<String, ArrayList<ItemStack>> entityMap = new HashMap<>();

    public static ArrayList<ItemStack> getLoot(NBTTagCompound nbt){
         return entityMap.get(nbt.getString("type"));
    }

    public static void addCustomLoot(ItemStack stack, int chance, String entityTypeCanonicalName){
       ArrayList<ItemStack> array = new ArrayList<>();

       if(entityMap.containsKey(entityTypeCanonicalName)){
           array = entityMap.get(entityTypeCanonicalName);
       }
       for(int i = 0; i < chance; i++){
           array.add(stack);

       }
       entityMap.putIfAbsent(entityTypeCanonicalName, array);
       entityMap.replace(entityTypeCanonicalName, array);

    }


    public static void initDefaultLoot() {
        MyMod.logger.info("Adding plushy default loot");
        addCustomLoot(new ItemStack(Items.EGG), 27, EntityChicken.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FEATHER), 18, EntityChicken.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.CHICKEN), 9, EntityChicken.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.PORKCHOP), 11, EntityPig.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BEEF), 9, EntityCow.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.LEATHER), 9, EntityCow.class.getCanonicalName());

        addCustomLoot(new ItemStack(Blocks.WOOL), 10, EntitySheep.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.MUTTON), 9, EntitySheep.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 12, EntityBat.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.RABBIT_HIDE), 9, EntityRabbit.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.RABBIT), 9, EntityRabbit.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.RABBIT_FOOT), 3, EntityRabbit.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.FEATHER), 22, EntityParrot.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.DYE),17, EntitySquid.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 12, EntityHorse.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 12, EntityMule.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 12, EntityDonkey.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 12, EntityOcelot.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 12, EntityLlama.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.FISH), 9, EntityPolarBear.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 1), 9, EntityPolarBear.class.getCanonicalName());

        addCustomLoot(new ItemStack(Blocks.RED_MUSHROOM), 9, EntityMooshroom.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BEEF), 8, EntityMooshroom.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.LEATHER), 8, EntityMooshroom.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BLAZE_ROD), 5, EntityBlaze.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BLAZE_POWDER), 8, EntityBlaze.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.STRING), 12, EntityCaveSpider.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.SPIDER_EYE), 9, EntityCaveSpider.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.STRING), 15, EntitySpider.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.SPIDER_EYE), 7, EntitySpider.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.GUNPOWDER), 9, EntityCreeper.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.dragonnugget), 1, EntityDragon.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.DRAGON_BREATH), 1, EntityDragon.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.nethernugget), 2, EntityWither.class.getCanonicalName());
        addCustomLoot(new ItemStack(ModItems.withernugget), 5, EntityWither.class.getCanonicalName());
        addCustomLoot(new ItemStack(Blocks.SOUL_SAND), 2, EntityWither.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.withernugget), 8, EntityWitherSkeleton.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.COAL), 5, EntityWitherSkeleton.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BONE), 5, EntityWitherSkeleton.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BONE), 9, EntitySkeleton.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.ARROW), 9, EntitySkeleton.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BOW), 1, EntitySkeleton.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ENDER_PEARL), 3, EntityEnderman.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ENDER_PEARL), 1, EntityEndermite.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.TOTEM_OF_UNDYING), 1, EntityEvoker.class.getCanonicalName());
        addCustomLoot(new ItemStack(ModItems.emeraldnugget), 1, EntityEvoker.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.GHAST_TEAR), 2, EntityGhast.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GUNPOWDER), 5, EntityGhast.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 10, EntityZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_INGOT), 1, EntityZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SHOVEL), 1, EntityZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.CARROT), 1, EntityZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.POTATO), 1, EntityZombie.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 30, EntityGiantZombie.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.FISH, 1, 1), 3, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 2), 2, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 3), 1, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH), 3, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.PRISMARINE_CRYSTALS, 1, 3), 4, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.PRISMARINE_SHARD), 4, EntityGuardian.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 10, EntityHusk.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_INGOT), 1, EntityHusk.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SHOVEL), 1, EntityHusk.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.CARROT), 1, EntityHusk.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.POTATO), 1, EntityHusk.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.IRON_INGOT), 3, EntityIronGolem.class.getCanonicalName());
        addCustomLoot(new ItemStack(Blocks.RED_FLOWER), 7, EntityIronGolem.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.MAGMA_CREAM), 5, EntityMagmaCube.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.SHULKER_SHELL), 4, EntityShulker.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.SLIME_BALL), 5, EntitySlime.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.SNOWBALL), 9,EntitySnowman.class.getCanonicalName());
        addCustomLoot(new ItemStack(Blocks.PUMPKIN), 2, EntitySnowman.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BONE), 9, EntityStray.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.ARROW), 9, EntityStray.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BOW), 1, EntityStray.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.IRON_SWORD), 1, EntityVex.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.GLASS_BOTTLE), 3, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GLOWSTONE_DUST), 1, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GUNPOWDER), 2, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.REDSTONE), 1, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.SPIDER_EYE), 2, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.STICK), 3, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.SUGAR), 2, EntityWitch.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.GOLD_NUGGET), 6, EntityPigZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GOLD_INGOT), 1, EntityPigZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GOLDEN_SWORD), 1, EntityPigZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GOLDEN_HELMET), 1, EntityPigZombie.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 12, EntityZombieHorse.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BONE), 12, EntitySkeletonHorse.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.emeraldnugget), 1, EntityVillager.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.emeraldnugget), 1, EntityVindicator.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SWORD), 1, EntityVindicator.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_AXE), 1, EntityVindicator.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_PICKAXE), 1, EntityVindicator.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SHOVEL), 1, EntityVindicator.class.getCanonicalName());


        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 10, EntityZombieVillager.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_INGOT), 1, EntityZombieVillager.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SHOVEL), 1, EntityZombieVillager.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.CARROT), 1, EntityZombieVillager.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.POTATO), 1, EntityZombieVillager.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.FISH, 1, 1), 4, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 2), 3, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 3), 2, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH), 4, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.PRISMARINE_CRYSTALS, 1, 3), 5, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.PRISMARINE_SHARD), 5, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Blocks.SPONGE, 1, 1), 5, EntityElderGuardian.class.getCanonicalName());

    }


}
