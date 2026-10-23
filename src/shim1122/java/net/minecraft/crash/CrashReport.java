package net.minecraft.crash;

public class CrashReport {
    private final String description;
    private final Throwable cause;

    public CrashReport(String description, Throwable cause) {
        this.description = description;
        this.cause = cause;
    }

    public String getDescription() {
        return description;
    }

    public Throwable getCause() {
        return cause;
    }

    public CrashReportCategory getCategory() {
        return new CrashReportCategory(description);
    }
}
