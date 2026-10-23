package net.minecraft.util;

public enum Mirror {
    NONE,
    LEFT_RIGHT,
    FRONT_BACK;

    public Rotation toRotation(EnumFacing facing) {
        EnumFacing.Axis axis = facing.getAxis();
        if (this == LEFT_RIGHT && axis == EnumFacing.Axis.Z) {
            return Rotation.CLOCKWISE_180;
        }
        if (this == FRONT_BACK && axis == EnumFacing.Axis.X) {
            return Rotation.CLOCKWISE_180;
        }
        return Rotation.NONE;
    }

    public EnumFacing mirror(EnumFacing facing) {
        return switch (this) {
            case LEFT_RIGHT -> facing == EnumFacing.EAST ? EnumFacing.WEST
                    : facing == EnumFacing.WEST ? EnumFacing.EAST : facing;
            case FRONT_BACK -> facing == EnumFacing.NORTH ? EnumFacing.SOUTH
                    : facing == EnumFacing.SOUTH ? EnumFacing.NORTH : facing;
            default -> facing;
        };
    }
}
