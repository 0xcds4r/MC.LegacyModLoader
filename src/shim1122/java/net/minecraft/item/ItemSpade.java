package net.minecraft.item;

public class ItemSpade extends Item {
    protected final ToolMaterial material;

    public ItemSpade(ToolMaterial material) {
        this.material = material;
        setMaxStackSize(1);
        setMaxDamage(material.getMaxUses());
    }

    public ToolMaterial getToolMaterial() {
        return material;
    }

    @Override
    public dev.legacyloader.bridge.LegacyToolKind legacyToolKind() {
        return dev.legacyloader.bridge.LegacyToolKind.SHOVEL;
    }

    @Override
    public String legacyToolMaterial() {
        return material.name();
    }

    @Override
    public float legacyAttackDamage() {
        return 1.5F + material.getAttackDamage();
    }

    @Override
    public float legacyAttackSpeed() {
        return -3.0F;
    }
}
