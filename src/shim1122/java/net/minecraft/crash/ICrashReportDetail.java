package net.minecraft.crash;

public interface ICrashReportDetail<T> {
    T call() throws Exception;
}
