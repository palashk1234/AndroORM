package palashk1234.androorm.engine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import palashk1234.androorm.annotations.Column;
import palashk1234.androorm.models.OrmConfigModel;
import palashk1234.androorm.utils.AndroUtils;
import palashk1234.androorm.utils.FieldType;
import palashk1234.androorm.utils.QueryMaker;

/**
 * Created by palash on 16 Feb 2017.
 * <p>
 * This Class is a singleton pattern class for running the Database instance in background
 * Initialize the DbEngine in Application Class as
 * <B> DbEngine.start(getApplicationContext(), "MyDatabase"); </B>
 */
public class DbEngine {

    private static final String TAG = "DbEngine";
    private static DbEngine INSTANCE = null;
    private String dataBaseName;
    private Context context;
    private DbHelper dbHelper;

    /**
     * Private Constructor for DbEngine Class to make it singleton.
     *
     * @param context      Instance of Context for Further Use
     * @param dataBaseName Name of database in String.
     */
    private DbEngine(Context context, String dataBaseName) {
        this.context = context;
        this.dataBaseName = dataBaseName;
        dbHelper = new DbHelper(context, dataBaseName);
    }

    /**
     * Method for initializing the DbEngine instance once for all.
     *
     * @param context      Instance of Context for Further Use
     * @param dataBaseName Name of database in String.
     */
    public static void start(Context context, String dataBaseName) {
        if (AndroUtils.isNull(INSTANCE)) {
            INSTANCE = new DbEngine(context, dataBaseName);
        }
    }

    /**
     * Method for obtaining instance of DbEngine Class
     *
     * @return Instance of DbEngine class (Can be null if not start method is not called).
     */
    public static DbEngine getInstance() {
        return INSTANCE;
    }

    /**
     * Method to handle INSERT query.
     *
     * @param tableName        Name of table
     * @param lstOrmConfigModel Configuration model of the class (Auto generated in Builders).
     * @return boolean (whether data is inserted in Table or not).
     */
    public boolean processInsertQuery(String tableName, List<OrmConfigModel> lstOrmConfigModel) {
        boolean isInsertSuccess = false;
        if (!AndroUtils.isNull(tableName)) {
            if (!AndroUtils.isNull(context) && !AndroUtils.isNull(dataBaseName)) {
//                DbHelper dbHelper = new DbHelper(context, dataBaseName);
                if (isTablePresent(tableName)) {
                    ContentValues contentValues = generateContentValues(lstOrmConfigModel);
                    long insert = dbHelper.getWritableDatabase().insert(tableName, null, contentValues);
                    if (insert > 0) {
                        isInsertSuccess = true;
                        Log.d(TAG, "Data inserted successfully.");
                    } else {
                        isInsertSuccess = false;
                    }
                } else {
                    if (isTableCreateSuccess(tableName, lstOrmConfigModel)) {
                        isInsertSuccess = processInsertQuery(tableName, lstOrmConfigModel);
                    } else {
                        return false;
                    }
                }
            } else {
                Log.e(TAG, "DbEngine is not initialized in Application class.");
                isInsertSuccess = false;
            }
        } else {
            Log.e(TAG, "Inset failed. Table Name is empty..");
            isInsertSuccess = false;
        }
        return isInsertSuccess;
    }

    /**
     * Method to create table
     *
     * @param tableName        Name of Table
     * @param lstOrmConfigModel Configuration Model of the class (Auto generated in Builders).
     * @return Boolean (Whether Table is created in Database or not).
     */
    private boolean isTableCreateSuccess(String tableName, List<OrmConfigModel> lstOrmConfigModel) {
        boolean isTableCreateSuccess = false;
        if (!AndroUtils.isNull(tableName)) {
            String query = QueryMaker.makeCreateTableQuery(tableName, lstOrmConfigModel);
            if (!AndroUtils.isNullString(query)) {
                try {
                    dbHelper.getWritableDatabase().execSQL(query);
                    Log.d(TAG, tableName + " Table created Successfully..");
                    isTableCreateSuccess = true;
                } catch (Exception e) {
                    e.printStackTrace();
                    isTableCreateSuccess = false;
                    Log.e(TAG, "Table create failed..");
                }
            }
        }
        return isTableCreateSuccess;
    }

    /**
     * Method for generating instance of ContentValues for ConfigurationModel.
     *
     * @param lstOrmConfigModel Configuration Model of the class (Auto generated in Builders).
     * @return instance of ContentValues
     */
    private ContentValues generateContentValues(List<OrmConfigModel> lstOrmConfigModel) {
        ContentValues contentValues = new ContentValues();
        for (OrmConfigModel ormConfigModel : lstOrmConfigModel) {
            try {
                if (ormConfigModel.getFieldType() == FieldType.INTEGER) {
                    contentValues.put(ormConfigModel.getColumnName(), (Integer) ormConfigModel.getColumnValue());
                } else if (ormConfigModel.getFieldType() == FieldType.REAL) {
                    contentValues.put(ormConfigModel.getColumnName(), (Double) ormConfigModel.getColumnValue());
                } else if (ormConfigModel.getFieldType() == FieldType.TEXT) {
                    contentValues.put(ormConfigModel.getColumnName(), (String) ormConfigModel.getColumnValue());
                } else if (ormConfigModel.getFieldType() == FieldType.BLOB) {
                    contentValues.put(ormConfigModel.getColumnName(), (Byte) ormConfigModel.getColumnValue());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return contentValues;
    }

    /**
     * Method for verifying the table is present in Db or not.
     *
     * @param tableName Name of Table
     * @return Boolean (Is table present or not).
     */
    private boolean isTablePresent(String tableName) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        if (!AndroUtils.isNull(cursor) && cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method for handling Select Query.
     *
     * @param tableName        Name of the Table.
     * @param clazz            type of class
     * @param fields           Selection fields (Can be null).
     * @param where            where clause for query (Can be null).
     * @param lstOrmConfigModel ConfigurationModel of the class (Auto generated in Builders).
     * @return List of objects of type class
     */
    public List<?> processSelectQuery(String tableName, Class<?> clazz, String fields, String where, List<OrmConfigModel> lstOrmConfigModel) {
        List lstData = new ArrayList();
        if (!AndroUtils.isNull(tableName)) {
            if (!AndroUtils.isNull(context) && !AndroUtils.isNull(dataBaseName)) {
                if (isTablePresent(tableName)) {
                    String query = QueryMaker.makeSelectQuery(tableName, clazz, fields, where, lstOrmConfigModel);
                    if (!AndroUtils.isNullString(query)) {
                        try {
                            Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
                            lstData = processSelectionResult(cursor, clazz, fields, lstOrmConfigModel);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        Log.e(TAG, "Query making failed.");
                    }
                } else {
                    Log.e(TAG, "Table Not Found");
                }
            }
        }
        return lstData;
    }

    /**
     * Method for processing the data received in cursor after the SQL Selection operation.
     *
     * @param cursor           instance of cursor which having data.
     * @param clazz            type of class
     * @param fields           Selection Fields
     * @param lstOrmConfigModel Configuration Model of the class (Auto generated in Builders).
     * @return List of objects of type class
     */
    private List processSelectionResult(Cursor cursor, Class<?> clazz, String fields,
                                        List<OrmConfigModel> lstOrmConfigModel) {
        List lstData = new ArrayList();
        if (!AndroUtils.isNull(cursor) && cursor.getCount() != 0) {
            cursor.moveToFirst();
            Method[] method = clazz.getDeclaredMethods();
            Field[] fieldsOfClass = clazz.getDeclaredFields();
            try {
                Constructor<?> constructor = clazz.getConstructor();
//                Log.e(TAG, "Object-->" + object.toString());
                if (!AndroUtils.isNull(fields)) {
                    String[] selectionFields = fields.split("\\,");
                    do {
                        Object object = constructor.newInstance();
                        for (String selectionField : selectionFields) {
                            if (!AndroUtils.isNull(cursor.getColumnName(cursor.getColumnIndex(selectionField)))) {
                                for (Field field1 : fieldsOfClass) {
                                    Annotation annotation = field1.getAnnotation(Column.class);
                                    Column column = (Column) annotation;
                                    if (column.name().equalsIgnoreCase(selectionField)) {
                                        for (Method meth : method) {
                                            if (meth.getName().startsWith("set")
                                                    && meth.getName().length() == (field1.getName().length() + 3)) {

                                                if (meth.getName().toLowerCase()
                                                        .endsWith(field1.getName().toLowerCase())) {
                                                    // Method found
                                                    try {
                                                        if (column.fieldType() == FieldType.INTEGER) {
                                                            int value = cursor.getInt(cursor.getColumnIndex(column.name()));
                                                            meth.invoke(object, value);
                                                        } else if (column.fieldType() == FieldType.TEXT) {
                                                            String value = cursor.getString(cursor.getColumnIndex(column.name()));
                                                            meth.invoke(object, value);
                                                        } else if (column.fieldType() == FieldType.REAL) {
                                                            double value = cursor.getDouble(cursor.getColumnIndex(column.name()));
                                                            meth.invoke(object, value);
                                                        } else if (column.fieldType() == FieldType.BLOB) {
                                                            byte[] value = cursor.getBlob(cursor.getColumnIndex(column.name()));
                                                            meth.invoke(object, value);
                                                        }
                                                        break;
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        lstData.add(object);
                    } while (cursor.moveToNext());


                } else {
                    do {
                        Object object = constructor.newInstance();
                        for (Field field1 : fieldsOfClass) {
                            Annotation annotation = field1.getAnnotation(Column.class);
                            Column column = (Column) annotation;
                            if (!AndroUtils.isNull(cursor.getColumnName(cursor.getColumnIndex(column.name())))) {
                                for (Method meth : method) {
                                    if (meth.getName().startsWith("set")
                                            && meth.getName().length() == (field1.getName().length() + 3)) {

                                        if (meth.getName().toLowerCase()
                                                .endsWith(field1.getName().toLowerCase())) {
                                            // Method found
                                            try {
                                                if (column.fieldType() == FieldType.INTEGER) {
                                                    int value = cursor.getInt(cursor.getColumnIndex(column.name()));
                                                    meth.invoke(object, value);
                                                } else if (column.fieldType() == FieldType.TEXT) {
                                                    String value = cursor.getString(cursor.getColumnIndex(column.name()));
                                                    meth.invoke(object, value);
                                                } else if (column.fieldType() == FieldType.REAL) {
                                                    double value = cursor.getDouble(cursor.getColumnIndex(column.name()));
                                                    meth.invoke(object, value);
                                                } else if (column.fieldType() == FieldType.BLOB) {
                                                    byte[] value = cursor.getBlob(cursor.getColumnIndex(column.name()));
                                                    meth.invoke(object, value);
                                                }
                                                break;
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        lstData.add(object);
                    } while (cursor.moveToNext());
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return lstData;
    }


    /**
     * Helper class for obtaining database for operation.
     */
    private class DbHelper extends SQLiteOpenHelper {

        private DbHelper(Context context, String dataBaseName) {
            super(context, dataBaseName, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
    }

}
