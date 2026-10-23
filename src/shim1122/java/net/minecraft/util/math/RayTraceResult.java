package net.minecraft.util.math;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;

public class RayTraceResult {
    public Type typeOfHit;
    public EnumFacing sideHit;
    public Vec3d hitVec;
    public Entity entityHit;
    private BlockPos blockPos;

    public RayTraceResult(Vec3d hitVec, EnumFacing sideHit, BlockPos blockPos) {
        this.typeOfHit = Type.BLOCK;
        this.hitVec = hitVec;
        this.sideHit = sideHit;
        this.blockPos = blockPos;
    }

    public RayTraceResult(Entity entity) {
        this.typeOfHit = Type.ENTITY;
        this.entityHit = entity;
        this.hitVec = new Vec3d(entity.posX, entity.posY, entity.posZ);
    }

    public RayTraceResult(Vec3d hitVec, EnumFacing sideHit, BlockPos blockPos, boolean missed) {
        this.typeOfHit = missed ? Type.MISS : Type.BLOCK;
        this.hitVec = hitVec;
        this.sideHit = sideHit;
        this.blockPos = blockPos;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public Type getTypeOfHit() {
        return typeOfHit;
    }

    public enum Type {
        MISS,
        BLOCK,
        ENTITY
    }
}
