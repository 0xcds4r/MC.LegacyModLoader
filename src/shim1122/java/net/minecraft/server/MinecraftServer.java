package net.minecraft.server;

/**
 * Signature stub. Dedicated server integration belongs to a later phase; the
 * type exists so command descriptors resolve.
 */
public class MinecraftServer {
    public void addScheduledTask(Runnable task) {
        task.run();
    }
}
