package koopamillion.mymod.plugins;

import com.typesafe.config.ConfigException;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.swing.text.html.parser.Entity;

public class BasePlugin{

    protected  final String modid;
    protected  final String modname;

    public BasePlugin(String modid, String modname){
        this.modid = modid;
        this.modname = modname;
    }


    //helps me get items from the registries
    protected  String getPlushyModEntitiesCanonicalName(String id, String entityname) {
        String type;
        try{
             type = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(id + ":" + entityname)).getEntityClass().getCanonicalName();
        } catch(NullPointerException e){
             type = "";
        }

        return type;
    }

    protected  Block getBlock(String id, String name) {

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(id + ":" + name));
        return block == null ? Blocks.AIR : block;
    }

    protected  Block getBlock(String name) {

        return getBlock(modid, name);
    }

    protected  ItemStack getItemStack(String id, String name, int amount, int meta) {

        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(id + ":" + name));
        return item != null ? new ItemStack(item, amount, meta) : ItemStack.EMPTY;
    }

    protected  ItemStack getItemStack(String name, int amount, int meta) {

        return getItemStack(modid, name, amount, meta);
    }

    protected  ItemStack getItemStack(String name, int amount) {

        return getItemStack(modid, name, amount, 0);
    }

    protected  ItemStack getItemStack(String name) {

        return getItemStack(modid, name, 1, 0);
    }
}
