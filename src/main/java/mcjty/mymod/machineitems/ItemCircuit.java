package mcjty.mymod.machineitems;

import mcjty.mymod.MyMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class ItemCircuit extends Item {

    public ItemCircuit() {
        this.setCreativeTab(MyMod.tabEKoop);
        setRegistryName("circuit");        // The unique name (within your mod) that identifies this item
        setUnlocalizedName(MyMod.MODID + ".circuit");     // Used for localization (en_US.lang)

    }
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

}

