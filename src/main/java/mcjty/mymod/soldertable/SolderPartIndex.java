package mcjty.mymod.soldertable;

import net.minecraft.util.IStringSerializable;

public enum SolderPartIndex implements IStringSerializable {
    UNFORMED("unformed", 0, 0, 0),
    P000("p000", 0, 0, 0),
    P001("p001", 1, 0, 0);




    // Optimization
    public static final SolderPartIndex[] VALUES = SolderPartIndex.values();

    private final String name;
    private final int dx;
    private final int dy;
    private final int dz;

    SolderPartIndex(String name, int dx, int dy, int dz) {
        this.name = name;
        this.dx = dx;
        this.dy = dy;
        this.dz = dz;
    }

    public static SolderPartIndex getIndex(int dx, int dy, int dz) {
        return VALUES[dx*1 + dy*0 + dz +1];
        //4, 2
    }

    @Override
    public String getName() {
        return name;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getDz() {
        return dz;
    }
}
