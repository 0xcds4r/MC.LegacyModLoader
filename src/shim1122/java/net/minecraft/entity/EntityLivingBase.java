package net.minecraft.entity;

import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.Collection;
import java.util.Collections;

public class EntityLivingBase extends Entity {
    protected float health = 20.0F;
    protected float maxHealth = 20.0F;

    public EntityLivingBase(World world) {
        super(world);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public boolean isEntityAlive() {
        return !isDead && health > 0.0F;
    }

    public ItemStack getHeldItemMainhand() {
        return ItemStack.EMPTY;
    }

    public ItemStack getHeldItemOffhand() {
        return ItemStack.EMPTY;
    }

    public ItemStack getHeldItem(EnumHand hand) {
        return ItemStack.EMPTY;
    }

    public ItemStack getHeldItem(net.minecraft.inventory.EntityEquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    public void swingArm(EnumHand hand) {
    }

    public Collection<PotionEffect> getActivePotionEffects() {
        return Collections.emptyList();
    }

    public boolean isPotionActive(Potion potion) {
        return false;
    }

    public PotionEffect getActivePotionEffect(Potion potion) {
        return null;
    }

    public void addPotionEffect(PotionEffect effect) {
    }

    public void removePotionEffect(Potion potion) {
    }

    public IAttributeInstance getEntityAttribute(net.minecraft.entity.ai.attributes.IAttribute attribute) {
        return null;
    }
}
