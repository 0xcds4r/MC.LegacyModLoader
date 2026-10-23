package net.minecraft.potion;

public class PotionEffect {
    private final Potion potion;
    private int duration;
    private int amplifier;
    private boolean ambient;
    private boolean showParticles = true;

    public PotionEffect(Potion potion, int duration) {
        this(potion, duration, 0);
    }

    public PotionEffect(Potion potion, int duration, int amplifier) {
        this(potion, duration, amplifier, false, true);
    }

    public PotionEffect(Potion potion, int duration, int amplifier, boolean ambient, boolean showParticles) {
        this.potion = potion;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
        this.showParticles = showParticles;
    }

    public Potion getPotion() {
        return potion;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public boolean isAmbient() {
        return ambient;
    }

    public boolean doesShowParticles() {
        return showParticles;
    }

    public boolean onUpdate(net.minecraft.entity.EntityLivingBase entity) {
        if (duration > 0) {
            duration--;
            return true;
        }
        return false;
    }

    public void combine(PotionEffect other) {
        this.duration = Math.max(duration, other.duration);
        this.amplifier = Math.max(amplifier, other.amplifier);
    }
}
