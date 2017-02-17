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
public @interface Entity {
    String FieldName() default "";

    FieldType FieldType() default FieldType.TEXT;

    int FieldSize() default 0;

    boolean isPrimaryKey() default false;
}
