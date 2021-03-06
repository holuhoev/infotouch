package ru.hse.infotouch.ruz.util;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Is used to specify the mapped ruz-api-field for a entity field.
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface JsonField {
    /**
     * (Optional) The name ofBuildingAddress the ruz-api-field. Defaults to
     * the property or field name.
     */
    String name() default "";
}
