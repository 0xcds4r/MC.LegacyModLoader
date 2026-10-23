package net.minecraftforge.fluids;

import java.util.LinkedHashMap;
import java.util.Map;

public final class FluidRegistry {
    private static final Map<String, Fluid> FLUIDS = new LinkedHashMap<>();
    private static boolean universalBucketEnabled;

    private FluidRegistry() {
    }

    public static void enableUniversalBucket() {
        universalBucketEnabled = true;
    }

    public static boolean isUniversalBucketEnabled() {
        return universalBucketEnabled;
    }

    public static boolean registerFluid(Fluid fluid) {
        if (fluid == null) {
            return false;
        }
        return FLUIDS.putIfAbsent(fluid.getName(), fluid) == null;
    }

    public static Fluid getFluid(String name) {
        return FLUIDS.get(name);
    }

    public static boolean addBucketForFluid(Fluid fluid) {
        return fluid != null;
    }

    public static java.util.Set<Fluid> getBucketFluids() {
        return new java.util.LinkedHashSet<>(FLUIDS.values());
    }
}
