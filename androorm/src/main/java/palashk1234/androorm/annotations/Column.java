package palashk1234.androorm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import palashk1234.androorm.utils.FieldType;

/**
 * Created by palash on 16 Feb 2017.
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    String name() default "";

    FieldType fieldType() default FieldType.TEXT;

    int fieldSize() default 0;

    boolean isPrimaryKey() default false;
}
