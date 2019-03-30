package koopamillion.mymod.plugins;

import koopamillion.mymod.MyMod;

public class PluginInit {
    public static void init(){
        MyMod.logger.info("Adding support for other mods");
        new PluginDarkUtilities().init();
        new PluginDraconicEvolution().init();
        new PluginExtraUtilities2().init();
        new PluginMysticalAgriculture().init();
        new PluginMysticalAgradations().init();
        new PluginQuark().init();
        new PluginThermalExpansion().init();
        new PluginTinkersConstruct().init();
    }
}
