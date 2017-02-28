package palashk1234.androorm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import palashk1234.androorm.utils.FieldType;

/**
 * Created by palash on 16 Feb 2017.
 * <p>
 * <B>Annotation for declaring the Field represents Column in Database.</B>
 */

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Column {
    /**
     * Set name of Column
     *
     * @return Name of the Column.
     */
    String name();

    /**
     * Set data type of the column (Default is Text)
     *
     * @return data type of the column
     */
    FieldType fieldType() default FieldType.TEXT;

    /**
     * Set size of the column
     *
     * @return size of the column
     */
    int fieldSize() default 0;

    /**
     * Mark the column as primary key (Default is false)
     *
     * @return boolean
     */
    boolean isPrimaryKey() default false;
}
