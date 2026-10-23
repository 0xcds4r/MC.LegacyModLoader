package net.minecraft.item;

public class ItemPickaxe extends Item {
    protected final ToolMaterial material;

    public ItemPickaxe(ToolMaterial material) {
        this.material = material;
        setMaxStackSize(1);
        setMaxDamage(material.getMaxUses());
    }

    public ToolMaterial getToolMaterial() {
        return material;
    }

    @Override
    public dev.legacyloader.bridge.LegacyToolKind legacyToolKind() {
        return dev.legacyloader.bridge.LegacyToolKind.PICKAXE;
    }

    @Override
    public String legacyToolMaterial() {
        return material.name();
    }

    @Override
    public float legacyAttackDamage() {
        return 1.0F + material.getAttackDamage();
    }

    @Override
    public float legacyAttackSpeed() {
        return -2.8F;
    }

    public boolean canHarvestBlock(net.minecraft.block.Block block) {
        return true;
    }
}
