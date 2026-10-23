package net.minecraft.entity;

public enum EnumCreatureType {
    MONSTER(70, true, false),
    CREATURE(10, true, false),
    AMBIENT(15, false, false),
    WATER_CREATURE(5, true, false);

    private final int maxNumberOfCreature;
    private final boolean isPeacefulCreature;
    private final boolean isAnimal;

    EnumCreatureType(int max, boolean peaceful, boolean animal) {
        this.maxNumberOfCreature = max;
        this.isPeacefulCreature = peaceful;
        this.isAnimal = animal;
    }

    public int getMaxNumberOfCreature() {
        return maxNumberOfCreature;
    }

    public boolean getPeacefulCreature() {
        return isPeacefulCreature;
    }

    public boolean getAnimal() {
        return isAnimal;
    }

    public Class<? extends Entity> getCreatureClass() {
        return Entity.class;
    }
}
