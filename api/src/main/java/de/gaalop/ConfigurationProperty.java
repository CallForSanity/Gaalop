package de.gaalop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * You can annotate fields in your Plugin classes with this annotation to make those properties
 * configurable by the Gaalop application. Properties will be read from a configuration file
 * and then set using your classes setter methods for those fields.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ConfigurationProperty {
}
