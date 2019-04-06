package koopamillion.mymod.machineitems;

import koopamillion.mymod.MyMod;
import koopamillion.mymod.tools.ChemicalFormulaList;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class ItemSolderIngot extends Item {
    private static final Pattern COMPILE = Pattern.compile("@", Pattern.LITERAL);

    public ItemSolderIngot() {
        this.setCreativeTab(MyMod.tabEKoop);
        setRegistryName("solderingot");        // The unique name (within your mod) that identifies this item
        setTranslationKey(MyMod.MODID + ".solderingot");     // Used for localization (en_US.lang)

    }
    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }



    public int getMeltingPoint(){
        return 183;
    }
    public ChemicalFormulaList ch = new ChemicalFormulaList();

    protected void addInformationLocalized(List<String> tooltip, String key, Object... parameters) {
        String translated = I18n.format(key, parameters);
        translated = COMPILE.matcher(translated).replaceAll("\u00a7");
        Collections.addAll(tooltip, StringUtils.split(translated, "\n"));
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flags) {

       addInformationLocalized(tooltip, "Melting Point: "+ TextFormatting.AQUA +getMeltingPoint() +"°C");
      // addInformationLocalized(tooltip, ch.getChemicalInfo("solder", 1));
   //    addInformationLocalized(tooltip, ch.getChemicalInfo("solder", 2));
        for(int i = 0; i < ChemicalFormulaList.hashMap.get("solder").size(); i++){
            addInformationLocalized(tooltip, ChemicalFormulaList.hashMap.get("solder").get(i));
        }
    }
}
