package koopamillion.mymod.machineitems;

import koopamillion.mymod.MyMod;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemIronBalls extends ItemBalls {

    public ItemIronBalls(){

        setRegistryName("ironballs");        // The unique name (within your mod) that identifies this item
        setTranslationKey(MyMod.MODID + ".ironballs");     // Used for localization (en_US.lang)
        setMaxDamage(1200);


    }
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public int getTier(){
        return 2;
    }

    @Override
    public int getBonus() {
        return 20;
    }
}
