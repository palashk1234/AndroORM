package palashk1234.androorm.models;

import palashk1234.androorm.utils.FieldType;

/**
 * Created by palash on 16 Feb 2017.
 */

public class OrmConfigPojo {

    private String key;
    private FieldType fieldType;
    private int fieldSize;
    private boolean isPrimaryKey;
    private Object value;

    public OrmConfigPojo() {
    }

    public OrmConfigPojo(String key, FieldType fieldType, int fieldSize, boolean isPrimaryKey, Object value) {
        this.key = key;
        this.fieldType = fieldType;
        this.fieldSize = fieldSize;
        this.isPrimaryKey = isPrimaryKey;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public int getFieldSize() {
        return fieldSize;
    }

    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }
}
