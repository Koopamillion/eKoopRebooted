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
        GameRegistry.addSmelting(new ItemStack(ModItems.dustlead), new ItemStack(ModItems.ingotlead), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.duststone), new ItemStack(Blocks.STONE), 0.1f);
        GameRegistry.addSmelting(new ItemStack(ModItems.dustsilver), new ItemStack(ModItems.ingotsilver), 0.1f);


    }
}
