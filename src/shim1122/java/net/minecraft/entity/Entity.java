package net.minecraft.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Signature-compatible entity stub. Ticking, data watchers and save data are
 * bridged in a later phase; the class exists so entity-heavy mods can load and
 * register their types.
 */
public class Entity {
    public World world;
    public double posX;
    public double posY;
    public double posZ;
    public double motionX;
    public double motionY;
    public double motionZ;
    public boolean isDead;
    public boolean noClip;
    public float rotationYaw;
    public float rotationPitch;
    private int entityId;
    private UUID uniqueId = UUID.randomUUID();
    private String customName = "";
    private boolean sneaking;
    private AxisAlignedBB boundingBox = new AxisAlignedBB(0, 0, 0, 0.6, 1.8, 0.6);

    public Entity(World world) {
        this.world = world;
    }

    public World getEntityWorld() {
        return world;
    }

    public void setPosition(double x, double y, double z) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
    }

    public BlockPos getPosition() {
        return new BlockPos(posX, posY, posZ);
    }

    public Vec3d getPositionVector() {
        return new Vec3d(posX, posY, posZ);
    }

    public Vec3d getLookVec() {
        double yaw = Math.toRadians(rotationYaw);
        double pitch = Math.toRadians(rotationPitch);
        return new Vec3d(-Math.sin(yaw) * Math.cos(pitch), -Math.sin(pitch),
                Math.cos(yaw) * Math.cos(pitch));
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead() {
        this.isDead = true;
    }

    public int getEntityId() {
        return entityId;
    }

    public UUID getUniqueID() {
        return uniqueId;
    }

    public double getDistance(double x, double y, double z) {
        double dx = posX - x;
        double dy = posY - y;
        double dz = posZ - z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double getDistanceSq(BlockPos pos) {
        double dx = posX - (pos.getX() + 0.5);
        double dy = posY - (pos.getY() + 0.5);
        double dz = posZ - (pos.getZ() + 0.5);
        return dx * dx + dy * dy + dz * dz;
    }

    public void setPositionAndUpdate(double x, double y, double z) {
        setPosition(x, y, z);
    }

    public void setVelocity(double x, double y, double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

    public void onUpdate() {
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public boolean isInWater() {
        return false;
    }

    public boolean isInLava() {
        return false;
    }

    public boolean isBurning() {
        return false;
    }

    public void setCustomNameTag(String name) {
        this.customName = name == null ? "" : name;
    }

    public String getCustomNameTag() {
        return customName;
    }

    public String getName() {
        return getClass().getSimpleName();
    }

    public AxisAlignedBB getEntityBoundingBox() {
        return boundingBox;
    }

    public float getEyeHeight() {
        return 1.62F;
    }

    public void setEntityBoundingBox(AxisAlignedBB box) {
        this.boundingBox = box;
    }

    public void writeToNBT(NBTTagCompound compound) {
    }

    public void readFromNBT(NBTTagCompound compound) {
    }

    public void dismountRidingEntity() {
    }

    public boolean startRiding(Entity vehicle) {
        return false;
    }

    public Entity getRidingEntity() {
        return null;
    }
}
