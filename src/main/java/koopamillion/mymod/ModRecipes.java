package koopamillion.mymod;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init(){
        GameRegistry.addSmelting(new ItemStack(ModItems.siliconraw), new ItemStack(ModItems.siliconpure), 0.1f);

        GameRegistry.addSmelting(new ItemStack(ModItems.dusttin), new ItemStack(ModItems.ingottin), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustaluminium), new ItemStack(ModItems.ingotaluminium), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustantinomy), new ItemStack(ModItems.ingotantinomy), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustcopper), new ItemStack(ModItems.ingotcopper), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustiron), new ItemStack(Items.IRON_INGOT), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustgold), new ItemStack(Items.GOLD_INGOT), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustlead), new ItemStack(ModItems.ingotlead), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.duststone), new ItemStack(Blocks.STONE), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustsilver), new ItemStack(ModItems.ingotsilver), 0.1f);

        GameRegistry.addSmelting(new ItemStack(ModItems.dustchrome), new ItemStack(ModItems.ingotchrome), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dusttungsten), new ItemStack(ModItems.ingottungsten), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustlithium), new ItemStack(ModItems.ingotlithium), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustmagnesium), new ItemStack(ModItems.ingotmagnesium), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dusttitanium), new ItemStack(ModItems.ingottitanium), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustvanadium), new ItemStack(ModItems.ingotvanadium), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustpalladium), new ItemStack(ModItems.ingotpalladium), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustiridium), new ItemStack(ModItems.ingotiridium), 0.1f);



    }
}
