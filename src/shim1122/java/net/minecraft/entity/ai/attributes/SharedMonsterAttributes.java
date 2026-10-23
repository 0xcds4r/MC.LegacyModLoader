package net.minecraft.entity.ai.attributes;

public class SharedMonsterAttributes {
    public static final IAttribute MAX_HEALTH = attribute("generic.maxHealth");
    public static final IAttribute FOLLOW_RANGE = attribute("generic.followRange");
    public static final IAttribute KNOCKBACK_RESISTANCE = attribute("generic.knockbackResistance");
    public static final IAttribute MOVEMENT_SPEED = attribute("generic.movementSpeed");
    public static final IAttribute FLYING_SPEED = attribute("generic.flyingSpeed");
    public static final IAttribute ATTACK_DAMAGE = attribute("generic.attackDamage");
    public static final IAttribute ATTACK_SPEED = attribute("generic.attackSpeed");
    public static final IAttribute ARMOR = attribute("generic.armor");
    public static final IAttribute ARMOR_TOUGHNESS = attribute("generic.armorToughness");
    public static final IAttribute LUCK = attribute("generic.luck");

    private static IAttribute attribute(String name) {
        return new IAttribute() {
            @Override
            public String getName() {
                return name;
            }

            @Override
            public double clampValue(double value) {
                return value;
            }

            @Override
            public double getDefaultValue() {
                return 0.0D;
            }

            @Override
            public boolean getShouldWatch() {
                return false;
            }
        };
    }
}
