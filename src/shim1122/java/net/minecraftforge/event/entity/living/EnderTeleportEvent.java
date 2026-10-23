package net.minecraftforge.event.entity.living;

import net.minecraft.entity.EntityLivingBase;

public class EnderTeleportEvent extends LivingEvent {
    private double targetX;
    private double targetY;
    private double targetZ;
    private float attackDamage;

    public EnderTeleportEvent(EntityLivingBase entity, double targetX, double targetY,
                              double targetZ, float attackDamage) {
        super(entity);
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
        this.attackDamage = attackDamage;
    }

    public double getTargetX() {
        return targetX;
    }

    public void setTargetX(double targetX) {
        this.targetX = targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public void setTargetY(double targetY) {
        this.targetY = targetY;
    }

    public double getTargetZ() {
        return targetZ;
    }

    public void setTargetZ(double targetZ) {
        this.targetZ = targetZ;
    }

    public float getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(float attackDamage) {
        this.attackDamage = attackDamage;
    }
}
