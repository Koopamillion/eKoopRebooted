package koopamillion.mymod.dna;

import koopamillion.mymod.furnace.FurnaceState;
import net.minecraft.util.IStringSerializable;

public enum DNAState implements IStringSerializable {
    OFF("off"),
    WORKING("working");

    //optimization
    public static final DNAState[] VALUES = DNAState.values();
    private final String name;
    DNAState(String name){
        this.name = name;
    }
    @Override
    public String getName(){
        return name;
    }
}
