package net.minecraft.util;

public enum EnumFacing {
    DOWN(0, 0, -1, 0),
    UP(1, 0, 1, 0),
    NORTH(2, 0, 0, -1),
    SOUTH(3, 0, 0, 1),
    WEST(4, -1, 0, 0),
    EAST(5, 1, 0, 0);

    private final int index;
    private final int offsetX;
    private final int offsetY;
    private final int offsetZ;

    EnumFacing(int index, int offsetX, int offsetY, int offsetZ) {
        this.index = index;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    public int getIndex() {
        return index;
    }

    public Axis getAxis() {
        if (offsetY != 0) {
            return Axis.Y;
        }
        return offsetX != 0 ? Axis.X : Axis.Z;
    }

    public enum Axis {
        X,
        Y,
        Z
    }

    public enum Plane implements com.google.common.base.Predicate<EnumFacing> {
        HORIZONTAL,
        VERTICAL;

        @Override
        public boolean apply(EnumFacing facing) {
            return this == HORIZONTAL ? facing.getAxis() != Axis.Y : facing.getAxis() == Axis.Y;
        }

        public EnumFacing[] facings() {
            return this == HORIZONTAL
                    ? new EnumFacing[]{NORTH, EAST, SOUTH, WEST}
                    : new EnumFacing[]{UP, DOWN};
        }
    }

    public int getFrontOffsetX() {
        return offsetX;
    }

    public int getFrontOffsetY() {
        return offsetY;
    }

    public int getFrontOffsetZ() {
        return offsetZ;
    }

    public EnumFacing getOpposite() {
        return switch (this) {
            case DOWN -> UP;
            case UP -> DOWN;
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case WEST -> EAST;
            case EAST -> WEST;
        };
    }

    public EnumFacing rotateY() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
            default -> this;
        };
    }

    public static EnumFacing byIndex(int index) {
        for (EnumFacing facing : values()) {
            if (facing.index == index) {
                return facing;
            }
        }
        return NORTH;
    }

    public static EnumFacing fromAngle(double angle) {
        return switch (((int) Math.round(angle / 90.0D)) & 3) {
            case 0 -> SOUTH;
            case 1 -> WEST;
            case 2 -> NORTH;
            default -> EAST;
        };
    }
}
