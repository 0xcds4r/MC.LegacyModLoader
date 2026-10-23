package net.minecraft.util;

public class DamageSource {
    public static final DamageSource GENERIC = new DamageSource("generic");
    public static final DamageSource MAGIC = new DamageSource("magic").setMagicDamage();
    public static final DamageSource IN_FIRE = new DamageSource("inFire").setFireDamage();
    public static final DamageSource LIGHTNING_BOLT = new DamageSource("lightningBolt");
    public static final DamageSource ON_FIRE = new DamageSource("onFire").setFireDamage();
    public static final DamageSource LAVA = new DamageSource("lava").setFireDamage();
    public static final DamageSource HOT_FLOOR = new DamageSource("hotFloor").setFireDamage();
    public static final DamageSource IN_WALL = new DamageSource("inWall");
    public static final DamageSource CRAMMING = new DamageSource("cramming");
    public static final DamageSource DROWN = new DamageSource("drown");
    public static final DamageSource STARVE = new DamageSource("starve");
    public static final DamageSource CACTUS = new DamageSource("cactus");
    public static final DamageSource FALL = new DamageSource("fall");
    public static final DamageSource FLY_INTO_WALL = new DamageSource("flyIntoWall");
    public static final DamageSource OUT_OF_WORLD = new DamageSource("outOfWorld");
    public static final DamageSource ANVIL = new DamageSource("anvil");
    public static final DamageSource FALLING_BLOCK = new DamageSource("fallingBlock");
    public static final DamageSource DRAGON_BREATH = new DamageSource("dragonBreath").setMagicDamage();
    public static final DamageSource FIREWORKS = new DamageSource("fireworks").setExplosion();

    private final String damageType;
    private boolean magicDamage;
    private boolean fireDamage;
    private boolean explosion;
    private boolean unblockable;
    private boolean damageBypassesArmor;

    public DamageSource(String damageType) {
        this.damageType = damageType;
    }

    public String getDamageType() {
        return damageType;
    }

    public boolean isMagicDamage() {
        return magicDamage;
    }

    public boolean isFireDamage() {
        return fireDamage;
    }

    public boolean isExplosion() {
        return explosion;
    }

    public boolean isUnblockable() {
        return unblockable;
    }

    public boolean canHarmInCreative() {
        return unblockable;
    }

    public DamageSource setMagicDamage() {
        this.magicDamage = true;
        return this;
    }

    public DamageSource setFireDamage() {
        this.fireDamage = true;
        return this;
    }

    public DamageSource setExplosion() {
        this.explosion = true;
        return this;
    }

    public DamageSource setDamageBypassesArmor() {
        this.damageBypassesArmor = true;
        return this;
    }

    public DamageSource setDamageAllowedInCreativeMode() {
        this.unblockable = true;
        return this;
    }
}
