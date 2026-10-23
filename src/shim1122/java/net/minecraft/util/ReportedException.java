package net.minecraft.util;

public class ReportedException extends RuntimeException {
    public ReportedException(Throwable cause) {
        super(cause);
    }

    public ReportedException(String message) {
        super(message);
    }

    public net.minecraft.crash.CrashReport getCrashReport() {
        return null;
    }
}
