package palashk1234.androorm.example;

import palashk1234.androorm.annotations.Entity;
import palashk1234.androorm.annotations.Table;
import palashk1234.androorm.utils.FieldType;

/**
 * Created by palash on 17 Feb 2017.
 */

@Table(TableName = "TEST_TABLE")
public class TestPojo {

    @Entity(FieldName = "COUNT", FieldType = FieldType.INTEGER, FieldSize = 3, isPrimaryKey = true)
    private int count;

    @Entity(FieldName = "VALUE", FieldType = FieldType.TEXT)
    private String value;





}
