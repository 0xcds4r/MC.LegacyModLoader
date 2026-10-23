package dev.legacyloader.discovery;

public record Diagnostic(Severity severity, String message) {
    public enum Severity {
        INFO,
        WARN,
        ERROR
    }

    public static Diagnostic info(String message) {
        return new Diagnostic(Severity.INFO, message);
    }

    public static Diagnostic warn(String message) {
        return new Diagnostic(Severity.WARN, message);
    }

    public static Diagnostic error(String message) {
        return new Diagnostic(Severity.ERROR, message);
    }
}
