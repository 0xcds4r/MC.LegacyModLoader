package net.minecraftforge.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public interface ISpecialArmor {
    ArmorProperties getProperties(EntityLivingBase player, ItemStack armor,
                                  DamageSource source, double damage, int slot);

    int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot);

    void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot);

    class ArmorProperties {
        public int priority = 0;
        public double absorptionRatio = 0.0D;
        public double damageLimit = Double.MAX_VALUE;
        public boolean absorbMax = true;

        public ArmorProperties(int priority, double absorptionRatio, int damageLimit) {
            this.priority = priority;
            this.absorptionRatio = absorptionRatio;
            this.damageLimit = damageLimit;
        }
    }
}
