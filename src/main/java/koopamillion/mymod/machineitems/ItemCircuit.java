package koopamillion.mymod.machineitems;

import koopamillion.mymod.MyMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.ai.EntityAILookAtVillager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;


public class ItemCircuit extends Item {

    public ItemCircuit() {
        this.setCreativeTab(MyMod.tabEKoop);
        setRegistryName("circuit");        // The unique name (within your mod) that identifies this item
        setTranslationKey(MyMod.MODID + ".circuit");     // Used for localization (en_US.lang)

    }
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}

