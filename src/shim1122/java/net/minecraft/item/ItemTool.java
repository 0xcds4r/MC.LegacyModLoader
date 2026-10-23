package net.minecraft.item;

import net.minecraft.block.Block;

import java.util.Set;

public class ItemTool extends Item {
    private final Set<Block> effectiveBlocks;

    public ItemTool(float attackDamageIn, float attackSpeedIn, ToolMaterial material, Set<Block> effectiveBlocks) {
        this.effectiveBlocks = effectiveBlocks;
        setMaxStackSize(1);
        setMaxDamage(material.getMaxUses());
    }

    public Set<Block> getEffectiveBlocks() {
        return effectiveBlocks;
    }

    public float getDestroySpeed(ItemStack stack, net.minecraft.block.state.IBlockState state) {
        return 1.0F;
    }
}
