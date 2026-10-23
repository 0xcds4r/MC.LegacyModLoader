package net.minecraftforge.fml.common.eventhandler;

/**
 * Base event shim. Cancellation state is real; event objects for modern hooks
 * will be introduced one by one as bridge coverage grows.
 */
public class Event {
    private boolean canceled;

    public boolean isCancelable() {
        return false;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }
}
