package net.minecraftforge.fml.common;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1.12.2 proxy injection marker. The loader instantiates the class named for
 * the current side and assigns it to the annotated static field.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SidedProxy {
    String clientSide() default "";

    String serverSide() default "";

    String modId() default "";
}
