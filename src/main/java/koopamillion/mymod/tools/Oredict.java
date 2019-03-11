package koopamillion.mymod.tools;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Oredict {


    public static void init(){
        MyMod.logger.info("Doing the ore stuff!");

        addOre(ModItems.enderingot, "ingotEnderSolder");
        addOre(ModItems.itemSolder, "ingotSolder");
        addOre(ModItems.itemCircuit, "circuitBasic");
        addOre(ModItems.salt, "salt");
        addOre(ModItems.salt, "dustSalt");
        addOre(ModItems.siliconraw, "silicon");
    }


    private static void addOre(Item stack, String name){

        OreDictionary.registerOre(name, stack);
    }
}
