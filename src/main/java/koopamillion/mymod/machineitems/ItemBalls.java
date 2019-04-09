package koopamillion.mymod.machineitems;

import koopamillion.mymod.MyMod;
import net.minecraft.item.Item;

public class ItemBalls extends Item {

    public ItemBalls(){
        this.setCreativeTab(MyMod.tabEKoop);
        setMaxStackSize(1);
    }

    public int getTier(){
        return 0;
    }

    public int getBonus(){
        return 0;
    }

}
