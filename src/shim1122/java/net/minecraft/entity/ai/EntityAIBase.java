package net.minecraft.entity.ai;

public abstract class EntityAIBase {
    private int mutexBits;

    public abstract boolean shouldExecute();

    public boolean shouldContinueExecuting() {
        return shouldExecute();
    }

    public boolean isInterruptible() {
        return true;
    }

    public void startExecuting() {
    }

    public void resetTask() {
    }

    public void updateTask() {
    }

    public void setMutexBits(int bits) {
        this.mutexBits = bits;
    }

    public int getMutexBits() {
        return mutexBits;
    }
}
