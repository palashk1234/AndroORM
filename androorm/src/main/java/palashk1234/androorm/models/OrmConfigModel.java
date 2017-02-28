package palashk1234.androorm.models;

import palashk1234.androorm.utils.FieldType;

/**
 * Created by palash on 16 Feb 2017.
 * <p>
 * This is the Configuration Model Class.
 * Builders Classes will use this class to generate the information about the class.
 */
public class OrmConfigModel {

    private String columnName;
    private FieldType fieldType;
    private int fieldSize;
    private boolean isPrimaryKey;
    private Object columnValue;

    /**
     * Empty Constructor of the class OrmConfigModel
     */
    public OrmConfigModel() {
    }

    /**
     * Parametrized Constructor of the class OrmConfigModel
     *
     * @param columnName   Name of the column.
     * @param fieldType    Data type of the column.
     * @param fieldSize    size of the column.
     * @param isPrimaryKey boolean to set column as primary key.
     * @param columnValue  value for the column.
     */
    public OrmConfigModel(String columnName, FieldType fieldType, int fieldSize, boolean isPrimaryKey, Object columnValue) {
        this.columnName = columnName;
        this.fieldType = fieldType;
        this.fieldSize = fieldSize;
        this.isPrimaryKey = isPrimaryKey;
        this.columnValue = columnValue;
    }

    /**
     * Returns the Column Name
     *
     * @return String.
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Method to Set the Column Name.
     *
     * @param columnName Name of the column.
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Returns the value for the column.
     *
     * @return Object.
     */
    public Object getColumnValue() {
        return columnValue;
    }

    /**
     * Method to Set the value for the column.
     *
     * @param columnValue value for the column.
     */
    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    /**
     * Returns the data type of the column
     *
     * @return enum
     */
    public FieldType getFieldType() {
        return fieldType;
    }

    /**
     * Method to Set the data type of the column.
     *
     * @param fieldType data type of the column.
     */
    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * Returns size of the column.
     *
     * @return int.
     */
    public int getFieldSize() {
        return fieldSize;
    }

    /**
     * Method to Set the size of the column.
     *
     * @param fieldSize size of the column.
     */
    public void setFieldSize(int fieldSize) {
        this.fieldSize = fieldSize;
    }

    /**
     * Method to set value for the column.
     *
     * @param value value for the column.
     */
    public void setValue(Object value) {
        this.columnValue = value;
    }

    /**
     * Returns whether the column is marked as Primary key or not.
     *
     * @return boolean.
     */
    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    /**
     * Method to mark column as primary key.
     *
     * @param primaryKey boolean to mark column as primary key.
     */
    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }
}
