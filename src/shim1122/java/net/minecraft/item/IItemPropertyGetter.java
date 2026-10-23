package net.minecraft.item;

public interface IItemPropertyGetter {
    float apply(ItemStack stack, net.minecraft.world.World world, net.minecraft.entity.EntityLivingBase entity);
}
