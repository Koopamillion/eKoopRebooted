package mcjty.mymod.tools;

import mcjty.mymod.crafting.SolderRecipe;

import java.util.ArrayList;
import java.util.List;

public class ChemicalFormulaList {

    private static ArrayList chemicalformula = new ArrayList<>();
    private static ArrayList ratiolist = new ArrayList<>();
    private static ArrayList chemicalstringindex = new ArrayList<>();

    public static void setChemicalStringIndex(){
        chemicalstringindex.add("solder");
    }
    public static void setChemicalformula() {
        chemicalformula.add("SnPb");
    }
    public static void setRatiolist(){
        ratiolist.add("2:1");
    }
    public static String getRatioList(int k){
        return ratiolist.get(k).toString();
    }
    public static String getChemicalformula(int k){
        return chemicalformula.get(k).toString();
    }
    public static String getChemicalInfo(String l, int x) {
        for (int i = 0; i < chemicalstringindex.size(); i++) {
            String j = chemicalstringindex.get(i).toString();
            if (j.equalsIgnoreCase(l)) {
                if(x == 2){return getRatioList(i);}else{
                    return getChemicalformula(i);
                }
            }
        }
        return "Unknown";
    }

    public static void initlist(){
        setChemicalformula();
        setRatiolist();
        setChemicalStringIndex();
    }

}
