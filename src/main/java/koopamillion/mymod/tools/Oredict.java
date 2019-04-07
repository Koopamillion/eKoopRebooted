package koopamillion.mymod.tools;

import koopamillion.mymod.ModItems;
import koopamillion.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class Oredict {


    public static void init(){
        MyMod.logger.info("Adding ore dictionary entries");

        addOre(ModItems.enderingot, "ingotEnderSolder");
        addOre(ModItems.itemSolder, "ingotSolder");
        addOre(ModItems.itemCircuit, "circuitBasic");
        addOre(ModItems.siliconpure, "waferSilicon");
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

        addOre(ModItems.tinyaluminium, "dustTinyAluminium");
        addOre(ModItems.tinyaluminium, "dustTinyAluminum");
        addOre(ModItems.tinyantinomy, "dustTinyAntimony");
        addOre(ModItems.tinycopper, "dustTinyCopper");
        addOre(ModItems.tinyiron, "dustTinyIron");
        addOre(ModItems.tinylead, "dustTinyLead");
        addOre(ModItems.tinysilver, "dustTinySilver");
        addOre(ModItems.tinystone, "dustTinyStone");
        addOre(ModItems.tinytin, "dustTinyTin");
        addOre(ModItems.tinyiridium, "dustTinyIridium");

        addOre(ModItems.tinytungsten, "dustTinyTungsten");
        addOre(ModItems.tinygold, "dustTinyGold");
        addOre(ModItems.dustgold, "dustGold");
        addOre(ModItems.tinyredstone, "dustTinyRedstone");
        addOre(ModItems.tinylithium, "dustTinyLithium");
        addOre(ModItems.dustend, "dustEndstone");
        addOre(ModItems.dustnether, "dustNetherrack");

        addOre(ModItems.dustaluminium, "dustAluminium");
        addOre(ModItems.dustaluminium, "dustAluminum");
        addOre(ModItems.dustantinomy, "dustAntimony");
        addOre(ModItems.dustcopper, "dustCopper");
        addOre(ModItems.dustiron, "dustIron");
        addOre(ModItems.dustlead, "dustLead");
        addOre(ModItems.dustsilver, "dustSilver");
        addOre(ModItems.duststone, "dustStone");
        addOre(ModItems.dusttin, "dustTin");
        addOre(ModItems.dustcharcoal, "dustCharcoal");
        addOre(ModItems.dustcoal, "dustCoal");
        addOre(ModItems.dustsulfur, "dustSulfur");
        addOre(ModItems.dustsaltpeter, "dustNiter");
        addOre(ModItems.dustsaltpeter, "dustSaltpeter");

        addOre(ModItems.dustchrome, "dustChrome");
        addOre(ModItems.dusttungsten, "dustTungsten");
        addOre(ModItems.dustlithium, "dustLithium");
        addOre(ModItems.dustmagnesium, "dustMagnesium");
        addOre(ModItems.dusttitanium, "dustTitanium");
        addOre(ModItems.dustvanadium, "dustVanadium");
        addOre(ModItems.dustpalladium, "dustPalladium");
        addOre(ModItems.dustiridium, "dustIridium");

        addOre(ModItems.ingotchrome, "ingotChrome");
        addOre(ModItems.ingottungsten, "ingotTungsten");
        addOre(ModItems.ingotlithium, "ingotLithium");
        addOre(ModItems.ingotmagnesium, "ingotMagnesium");
        addOre(ModItems.ingottitanium, "ingotTitanium");
        addOre(ModItems.ingotvanadium, "ingotVanadium");
        addOre(ModItems.ingotpalladium, "ingotPalladium");
        addOre(ModItems.ingotiridium, "ingotIridium");

        addOre(ModItems.ingotaluminium, "ingotAluminium");
        addOre(ModItems.ingotantinomy, "ingotAntimony");
        addOre(ModItems.ingotcopper, "ingotCopper");
        addOre(ModItems.ingotlead, "ingotLead");
        addOre(ModItems.ingotsilver, "ingotSilver");
        addOre(ModItems.ingottin, "ingotTin");

        addOre(ModItems.advancedcircuit, "circuitAdvanced");
    }


    private static void addOre(Item stack, String name){

        OreDictionary.registerOre(name, stack);
    }
    private static void addOre(Block stack, String name){

        OreDictionary.registerOre(name, stack);
    }
}
