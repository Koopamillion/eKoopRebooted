package koopamillion.mymod.tools;

import net.minecraftforge.energy.EnergyStorage;

public class MyEnergyStorage extends EnergyStorage {

    public MyEnergyStorage(int capacity, int maxReceive) {
        super(capacity, maxReceive, 0);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void consumePower(int energy) {
        this.energy -= energy;
        if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public boolean shouldGenerate(int capacity){
        return this.energy < capacity;
    }

    public void generatePower(int energy, int capacity){
        this.energy += energy ;
        if (this.energy >= capacity) {
            this.energy = capacity;
        }
    }


}
