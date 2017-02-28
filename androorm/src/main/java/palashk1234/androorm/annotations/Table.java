package palashk1234.androorm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by palash on 16 Feb 2017.
 * <p>
 * Annotation for declaring the Class represents Table in Database.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Table {
    /**
     * Set name of Table
     *
     * @return Name of the Table.
     */
    String name();
}
