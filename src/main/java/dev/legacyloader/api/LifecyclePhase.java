package dev.legacyloader.api;

/**
 * Emulated Forge 1.12.2 mod lifecycle, in call order.
 */
public enum LifecyclePhase {
    CONSTRUCT,
    PRE_INIT,
    INIT,
    POST_INIT,
    LOAD_COMPLETE,
    SERVER_ABOUT_TO_START,
    SERVER_STARTING,
    SERVER_STARTED
}
