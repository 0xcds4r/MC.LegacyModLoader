package net.minecraft.entity.ai.attributes;

public class AttributeModifier {
    private final java.util.UUID id;
    private final String name;
    private final double amount;
    private final int operation;

    public AttributeModifier(java.util.UUID id, String name, double amount, int operation) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.operation = operation;
    }

    public AttributeModifier(String name, double amount, int operation) {
        this(java.util.UUID.randomUUID(), name, amount, operation);
    }

    public java.util.UUID getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public int getOperation() {
        return operation;
    }
}
