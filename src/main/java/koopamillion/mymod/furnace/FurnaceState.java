package koopamillion.mymod.furnace;

import net.minecraft.util.IStringSerializable;

public enum FurnaceState implements IStringSerializable {
    OFF("off"),
    WORKING("working");

    //optimization
    public static final FurnaceState[] VALUES = FurnaceState.values();
    private final String name;
    FurnaceState(String name){
        this.name = name;
    }
    @Override
    public String getName(){
        return name;
    }
}
