package net.minecraft.util;

public enum Rotation {
    NONE,
    CLOCKWISE_90,
    CLOCKWISE_180,
    COUNTERCLOCKWISE_90;

    public Rotation add(Rotation other) {
        return values()[(ordinal() + other.ordinal()) & 3];
    }

    public EnumFacing rotate(EnumFacing facing) {
        return switch (this) {
            case CLOCKWISE_90 -> facing.rotateY();
            case CLOCKWISE_180 -> facing.getOpposite();
            case COUNTERCLOCKWISE_90 -> facing.rotateY().getOpposite();
            default -> facing;
        };
    }
}
