package net.minecraftforge.fml.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class FMLLog {
    private static final Logger LOG = LogManager.getLogger("legacy");

    private FMLLog() {
    }

    public static Logger getLogger() {
        return LOG;
    }

    public static void info(String format, Object... arguments) {
        LOG.info(format, arguments);
    }

    public static void warn(String format, Object... arguments) {
        LOG.warn(format, arguments);
    }

    public static void error(String format, Object... arguments) {
        LOG.error(format, arguments);
    }

    public static void log(Level level, String format, Object... arguments) {
        LOG.info(format, arguments);
    }

    public enum Level {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }
}
