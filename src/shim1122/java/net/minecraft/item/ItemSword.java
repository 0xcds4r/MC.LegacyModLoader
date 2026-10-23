package net.minecraft.item;

public class ItemSword extends Item {
    private final ToolMaterial material;

    public ItemSword(ToolMaterial material) {
        this.material = material;
        setMaxStackSize(1);
        setMaxDamage(material.getMaxUses());
    }

    public ToolMaterial getToolMaterial() {
        return material;
    }

    public float getAttackDamage() {
        return 3.0F + material.getAttackDamage();
    }

    @Override
    public dev.legacyloader.bridge.LegacyToolKind legacyToolKind() {
        return dev.legacyloader.bridge.LegacyToolKind.SWORD;
    }

    @Override
    public String legacyToolMaterial() {
        return material.name();
    }

    @Override
    public float legacyAttackDamage() {
        return getAttackDamage();
    }

    @Override
    public float legacyAttackSpeed() {
        return -2.4F;
    }
}
