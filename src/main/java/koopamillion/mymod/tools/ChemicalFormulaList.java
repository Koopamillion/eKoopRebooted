package koopamillion.mymod.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChemicalFormulaList {

   public static Map<String, List<String>> hashMap = new HashMap<>();
   public static List<String> a = new ArrayList<>();
   public static void init(){
       a.add("SnPb");
       a.add("2:1");
       hashMap.put("solder", a);
       a = new ArrayList<>();
       a.add("SolderEnder");
       a.add("1:1");
       hashMap.put("endersolder", a);
       a = new ArrayList<>();
       a.add("SolderAgSb");
       a.add("1:1:0.11");
       hashMap.put("silversolder", a);
   }

}
