package net.minecraft.item;

public class ItemHoe extends Item {
    protected final ToolMaterial material;

    public ItemHoe(ToolMaterial material) {
        this.material = material;
        setMaxStackSize(1);
        setMaxDamage(material.getMaxUses());
    }

    public ToolMaterial getToolMaterial() {
        return material;
    }

    @Override
    public dev.legacyloader.bridge.LegacyToolKind legacyToolKind() {
        return dev.legacyloader.bridge.LegacyToolKind.HOE;
    }

    @Override
    public String legacyToolMaterial() {
        return material.name();
    }

    @Override
    public float legacyAttackDamage() {
        return 0.0F;
    }

    @Override
    public float legacyAttackSpeed() {
        return -3.0F;
    }
}
