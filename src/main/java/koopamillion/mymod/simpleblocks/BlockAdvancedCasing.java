package koopamillion.mymod.simpleblocks;

import koopamillion.mymod.MyMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAdvancedCasing  extends Block {

    public static final ResourceLocation advancedcasing = new ResourceLocation(MyMod.MODID, "advancedcasing");

    public BlockAdvancedCasing() {
        super(Material.IRON); //super fetches the material.Iron from the block class (vanilla)
        // mymod:charger
        setRegistryName(advancedcasing);
        setTranslationKey(MyMod.MODID + ".advancedcasing");
        setHarvestLevel("pickaxe", 1);
        setCreativeTab(MyMod.tabEKoop);
        setHardness(50);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));

    }

}
