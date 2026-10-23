package net.minecraftforge.fml.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Forge 1.12.2 {@code @Mod} shim. All elements of the original annotation are
 * present so old classes can be reflected without {@code IncompleteAnnotationException}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mod {
    String modid();

    String name() default "";

    String version() default "";

    String dependencies() default "";

    boolean useMetadata() default false;

    String acceptedMinecraftVersions() default "";

    String acceptableRemoteVersions() default "*";

    String certificateFingerprint() default "";

    String updateJSON() default "";

    boolean clientSideOnly() default false;

    boolean serverSideOnly() default false;

    String modLanguage() default "java";

    String modLanguageAdapter() default "";

    boolean canBeDeactivated() default false;

    String guiFactory() default "";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface EventBusSubscriber {
        String modid() default "";

        net.minecraftforge.fml.relauncher.Side[] value() default {
                net.minecraftforge.fml.relauncher.Side.CLIENT,
                net.minecraftforge.fml.relauncher.Side.SERVER
        };

        Bus bus() default Bus.FORGE;

        enum Bus {
            FORGE,
            MOD
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface EventHandler {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Instance {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Metadata {
    }
}
