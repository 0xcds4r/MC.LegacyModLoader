package net.minecraft.entity.player;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityPlayer extends net.minecraft.entity.EntityLivingBase {
    public final InventoryPlayer inventory = new InventoryPlayer();
    public final PlayerCapabilities capabilities = new PlayerCapabilities();
    public int experienceLevel;
    public float experience;

    public EntityPlayer(World world) {
        super(world);
    }

    public boolean isCreative() {
        return capabilities.isCreativeMode;
    }

    public boolean isSpectator() {
        return false;
    }

    public ItemStack getHeldItemMainhand() {
        return inventory.getCurrentItem();
    }

    @Override
    public ItemStack getHeldItem(EntityEquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    public void sendStatusMessage(net.minecraft.util.text.ITextComponent message, boolean actionBar) {
    }
}
