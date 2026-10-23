package net.minecraft.util.math;

public class BlockPos extends Vec3i {
    public static final BlockPos ORIGIN = new BlockPos(0, 0, 0);

    public BlockPos(int x, int y, int z) {
        super(x, y, z);
    }

    public BlockPos(double x, double y, double z) {
        super(x, y, z);
    }

    public BlockPos(Vec3i source) {
        this(source.getX(), source.getY(), source.getZ());
    }

    public BlockPos add(int dx, int dy, int dz) {
        return new BlockPos(getX() + dx, getY() + dy, getZ() + dz);
    }

    public BlockPos add(BlockPos other) {
        return add(other.getX(), other.getY(), other.getZ());
    }

    public BlockPos offset(net.minecraft.util.EnumFacing facing) {
        return offset(facing, 1);
    }

    public BlockPos offset(net.minecraft.util.EnumFacing facing, int distance) {
        return new BlockPos(
                getX() + facing.getFrontOffsetX() * distance,
                getY() + facing.getFrontOffsetY() * distance,
                getZ() + facing.getFrontOffsetZ() * distance
        );
    }

    public BlockPos up() {
        return offset(net.minecraft.util.EnumFacing.UP);
    }

    public BlockPos down() {
        return offset(net.minecraft.util.EnumFacing.DOWN);
    }

    public BlockPos north() {
        return offset(net.minecraft.util.EnumFacing.NORTH);
    }

    public BlockPos south() {
        return offset(net.minecraft.util.EnumFacing.SOUTH);
    }

    public BlockPos west() {
        return offset(net.minecraft.util.EnumFacing.WEST);
    }

    public BlockPos east() {
        return offset(net.minecraft.util.EnumFacing.EAST);
    }

    public BlockPos toImmutable() {
        return this;
    }

    public static class MutableBlockPos extends BlockPos {
        public MutableBlockPos() {
            this(0, 0, 0);
        }

        public MutableBlockPos(int x, int y, int z) {
            super(x, y, z);
        }

        public MutableBlockPos setPos(int x, int y, int z) {
            return new MutableBlockPos(x, y, z);
        }
    }

    public static class PooledMutableBlockPos extends MutableBlockPos {
        public static PooledMutableBlockPos retain() {
            return new PooledMutableBlockPos();
        }

        public static PooledMutableBlockPos retain(double x, double y, double z) {
            return new PooledMutableBlockPos((int) Math.floor(x), (int) Math.floor(y), (int) Math.floor(z));
        }

        public PooledMutableBlockPos() {
            super();
        }

        public PooledMutableBlockPos(int x, int y, int z) {
            super(x, y, z);
        }

        public void release() {
        }
    }
}
