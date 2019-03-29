package koopamillion.mymod.tools;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Oredict {


    public static void init(){
        MyMod.logger.info("Adding ore dictionary entries");

        addOre(ModItems.enderingot, "ingotEnderSolder");
        addOre(ModItems.itemSolder, "ingotSolder");
        addOre(ModItems.itemCircuit, "circuitBasic");
        addOre(ModItems.salt, "dustSalt");
        addOre(ModItems.salt, "itemSalt");
        addOre(ModItems.salt, "foodSalt");
        addOre(ModItems.siliconraw, "itemSilicon");
        addOre(ModItems.emeraldnugget, "nuggetEmerald");
        addOre(ModItems.dragonnugget, "nuggetDragon");
        addOre(ModItems.dragonnugget, "nuggetDragonEgg");
        addOre(ModItems.nethernugget, "nuggetNetherStar");
        addOre(ModItems.dragonheartnugget, "nuggetDragonHeart");
        addOre(ModItems.withernugget, "nuggetWither");
    }


    private static void addOre(Item stack, String name){

        OreDictionary.registerOre(name, stack);
    }
}
