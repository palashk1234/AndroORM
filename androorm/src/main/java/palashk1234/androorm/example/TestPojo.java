package palashk1234.androorm.example;

import palashk1234.androorm.annotations.Column;
import palashk1234.androorm.annotations.Table;
import palashk1234.androorm.utils.FieldType;

/**
 * Created by palash on 17 Feb 2017.
 */

@Table(name = "TEST_TABLE")
public class TestPojo {

    @Column(name = "COUNT", fieldType = FieldType.INTEGER, fieldSize = 3, isPrimaryKey = true)
    private int count;

    @Column(name = "VALUE", fieldType = FieldType.TEXT)
    private String value;


}
