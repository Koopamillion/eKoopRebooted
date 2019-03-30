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
        addCustomLoot(new ItemStack(Items.EGG), 40, EntityChicken.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FEATHER), 30, EntityChicken.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.CHICKEN), 15, EntityChicken.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.PORKCHOP), 22, EntityPig.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BEEF), 16, EntityCow.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.LEATHER), 16, EntityCow.class.getCanonicalName());

        addCustomLoot(new ItemStack(Blocks.WOOL), 15, EntitySheep.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.MUTTON), 15, EntitySheep.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 15, EntityBat.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.RABBIT_HIDE), 15, EntityRabbit.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.RABBIT), 15, EntityRabbit.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.RABBIT_FOOT), 4, EntityRabbit.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.FEATHER), 32, EntityParrot.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.DYE),18, EntitySquid.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 15, EntityHorse.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 15, EntityMule.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 15, EntityDonkey.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 15, EntityOcelot.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.LEATHER), 15, EntityLlama.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.FISH), 15, EntityPolarBear.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 1), 15, EntityPolarBear.class.getCanonicalName());

        addCustomLoot(new ItemStack(Blocks.RED_MUSHROOM), 16, EntityMooshroom.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BEEF), 15, EntityMooshroom.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.LEATHER), 15, EntityMooshroom.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BLAZE_ROD), 6, EntityBlaze.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BLAZE_POWDER), 9, EntityBlaze.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.STRING), 12, EntityCaveSpider.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.SPIDER_EYE), 11, EntityCaveSpider.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.STRING), 16, EntitySpider.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.SPIDER_EYE), 8, EntitySpider.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.GUNPOWDER), 15, EntityCreeper.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.dragonnugget), 1, EntityDragon.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.DRAGON_BREATH), 1, EntityDragon.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.nethernugget), 2, EntityWither.class.getCanonicalName());
        addCustomLoot(new ItemStack(ModItems.withernugget), 5, EntityWither.class.getCanonicalName());
        addCustomLoot(new ItemStack(Blocks.SOUL_SAND), 2, EntityWither.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.withernugget), 8, EntityWitherSkeleton.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.COAL), 6, EntityWitherSkeleton.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BONE), 8, EntityWitherSkeleton.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BONE), 15, EntitySkeleton.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.ARROW), 15, EntitySkeleton.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BOW), 1, EntitySkeleton.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ENDER_PEARL), 5, EntityEnderman.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ENDER_PEARL), 2, EntityEndermite.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.TOTEM_OF_UNDYING), 1, EntityEvoker.class.getCanonicalName());
        addCustomLoot(new ItemStack(ModItems.emeraldnugget), 2, EntityEvoker.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.GHAST_TEAR), 4, EntityGhast.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GUNPOWDER), 8, EntityGhast.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 25, EntityZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_INGOT), 1, EntityZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SHOVEL), 1, EntityZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.CARROT), 1, EntityZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.POTATO), 1, EntityZombie.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 50, EntityGiantZombie.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.FISH, 1, 1), 4, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 2), 3, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 3), 2, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH), 4, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.PRISMARINE_CRYSTALS, 1, 3), 5, EntityGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.PRISMARINE_SHARD), 5, EntityGuardian.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 22, EntityHusk.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_INGOT), 1, EntityHusk.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SHOVEL), 1, EntityHusk.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.CARROT), 1, EntityHusk.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.POTATO), 1, EntityHusk.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.IRON_INGOT), 4, EntityIronGolem.class.getCanonicalName());
        addCustomLoot(new ItemStack(Blocks.RED_FLOWER), 9, EntityIronGolem.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.MAGMA_CREAM), 8, EntityMagmaCube.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.SHULKER_SHELL), 5, EntityShulker.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.SLIME_BALL), 9, EntitySlime.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.SNOWBALL), 20,EntitySnowman.class.getCanonicalName());
        addCustomLoot(new ItemStack(Blocks.PUMPKIN), 5, EntitySnowman.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BONE), 12, EntityStray.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.ARROW), 12, EntityStray.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.BOW), 1, EntityStray.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.IRON_SWORD), 1, EntityVex.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.GLASS_BOTTLE), 8, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GLOWSTONE_DUST), 3, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GUNPOWDER), 3, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.REDSTONE), 2, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.SPIDER_EYE), 3, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.STICK), 5, EntityWitch.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.SUGAR), 4, EntityWitch.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.GOLD_NUGGET), 8, EntityPigZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GOLD_INGOT), 1, EntityPigZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GOLDEN_SWORD), 1, EntityPigZombie.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.GOLDEN_HELMET), 1, EntityPigZombie.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 22, EntityZombieHorse.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.BONE), 18, EntitySkeletonHorse.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.emeraldnugget), 3, EntityVillager.class.getCanonicalName());

        addCustomLoot(new ItemStack(ModItems.emeraldnugget), 2, EntityVindicator.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SWORD), 1, EntityVindicator.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_AXE), 1, EntityVindicator.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_PICKAXE), 1, EntityVindicator.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SHOVEL), 1, EntityVindicator.class.getCanonicalName());


        addCustomLoot(new ItemStack(Items.ROTTEN_FLESH), 20, EntityZombieVillager.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_INGOT), 1, EntityZombieVillager.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.IRON_SHOVEL), 1, EntityZombieVillager.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.CARROT), 1, EntityZombieVillager.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.POTATO), 1, EntityZombieVillager.class.getCanonicalName());

        addCustomLoot(new ItemStack(Items.FISH, 1, 1), 5, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 2), 4, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH, 1, 3), 3, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.FISH), 5, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.PRISMARINE_CRYSTALS, 1, 3), 6, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Items.PRISMARINE_SHARD), 6, EntityElderGuardian.class.getCanonicalName());
        addCustomLoot(new ItemStack(Blocks.SPONGE, 1, 1), 6, EntityElderGuardian.class.getCanonicalName());

    }


}
