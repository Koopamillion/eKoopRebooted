package koopamillion.mymod;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

    public static void init(){
        GameRegistry.addSmelting(new ItemStack(ModItems.siliconraw), new ItemStack(ModItems.siliconpure), 0.2f);

    }
}
