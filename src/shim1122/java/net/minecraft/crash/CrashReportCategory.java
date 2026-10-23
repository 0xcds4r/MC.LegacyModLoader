package net.minecraft.crash;

public class CrashReportCategory {
    private final String name;

    public CrashReportCategory(String name) {
        this.name = name;
    }

    public void addCrashSection(String sectionName, Object value) {
    }

    public void addCrashSectionThrowable(String sectionName, Throwable throwable) {
    }

    public String getName() {
        return name;
    }
}
