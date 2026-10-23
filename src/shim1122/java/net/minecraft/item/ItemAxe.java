package net.minecraft.item;

public class ItemAxe extends Item {
    protected final ToolMaterial material;

    public ItemAxe(ToolMaterial material, float damage, float speed) {
        this.material = material;
        setMaxStackSize(1);
        setMaxDamage(material.getMaxUses());
    }

    public ToolMaterial getToolMaterial() {
        return material;
    }

    @Override
    public dev.legacyloader.bridge.LegacyToolKind legacyToolKind() {
        return dev.legacyloader.bridge.LegacyToolKind.AXE;
    }

    @Override
    public String legacyToolMaterial() {
        return material.name();
    }

    @Override
    public float legacyAttackDamage() {
        return 5.0F + material.getAttackDamage();
    }

    @Override
    public float legacyAttackSpeed() {
        return -3.0F;
    }
}
