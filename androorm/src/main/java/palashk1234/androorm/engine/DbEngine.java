package palashk1234.androorm.engine;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import palashk1234.androorm.models.OrmConfigPojo;
import palashk1234.androorm.utils.AndroUtils;
import palashk1234.androorm.utils.FieldType;
import palashk1234.androorm.utils.QueryMaker;

/**
 * Created by palash on 16 Feb 2017.
 */

public class DbEngine {

    private static final String TAG = "DbEngine";
    private static DbEngine INSTANCE = null;
    private String dataBaseName;
    private Context context;


    private DbEngine(Context context, String dataBaseName) {
        this.context = context;
        this.dataBaseName = dataBaseName;
    }

    public static void start(Context context, String DataBaseName) {
        if (AndroUtils.isNull(INSTANCE)) {
            INSTANCE = new DbEngine(context, DataBaseName);
        }
    }

    public static DbEngine getInstance() {
        return INSTANCE;
    }

    public boolean processInsertQuery(String tableName, List<OrmConfigPojo> lstOrmConfigPojo) {
        boolean isInsertSuccess = false;
        if (!AndroUtils.isNull(tableName)) {
            if (!AndroUtils.isNull(context) && !AndroUtils.isNull(dataBaseName)) {
                DbHelper dbHelper = new DbHelper(context, dataBaseName);

                if (isTablePresent(dbHelper, tableName)) {
                    ContentValues contentValues = generateContentValues(lstOrmConfigPojo);
                    long insert = dbHelper.getWritableDatabase().insert(tableName, null, contentValues);
                    if (insert > 0) {
                        isInsertSuccess = true;
                        Log.d(TAG, "Data inserted successfully.");
                    } else {
                        isInsertSuccess = false;
                    }
                } else {
                    if (isTableCreateSuccess(tableName, lstOrmConfigPojo, dbHelper)) {
                        isInsertSuccess = processInsertQuery(tableName, lstOrmConfigPojo);
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

    private boolean isTableCreateSuccess(String tableName, List<OrmConfigPojo> lstOrmConfigPojo, DbHelper dbHelper) {
        boolean isTableCreateSuccess = false;
        if (!AndroUtils.isNull(tableName)) {
            String query = QueryMaker.makeCreateTableQuery(tableName, lstOrmConfigPojo);
            if (!AndroUtils.isNullString(query)) {

                try {
                    dbHelper.getWritableDatabase().execSQL(query);
                    Log.d(TAG, tableName + " Table created Successfully..");
                    isTableCreateSuccess = true;
                }catch (Exception e){
                    e.printStackTrace();
                    isTableCreateSuccess = false;
                    Log.e(TAG, "Table create failed..");
                }

//                Cursor cursor = dbHelper.getWritableDatabase().rawQuery(query, null);
//                if (AndroUtils.isNull(cursor) && cursor.getCount() != 0) {
//                    isTableCreateSuccess = true;
//                    Log.d(TAG, tableName + " Table created Successfully..");
//                } else {
//                    Log.e(TAG, "Table create failed..");
//                }
            }
        }
        return isTableCreateSuccess;
    }

    private ContentValues generateContentValues(List<OrmConfigPojo> lstOrmConfigPojo) {
        ContentValues contentValues = new ContentValues();
        for (OrmConfigPojo ormConfigPojo : lstOrmConfigPojo) {
            if (ormConfigPojo.getFieldType() == FieldType.INTEGER) {
                contentValues.put(ormConfigPojo.getKey(), (Integer) ormConfigPojo.getValue());
            } else if (ormConfigPojo.getFieldType() == FieldType.REAL) {
                contentValues.put(ormConfigPojo.getKey(), (Double) ormConfigPojo.getValue());
            } else if (ormConfigPojo.getFieldType() == FieldType.TEXT) {
                contentValues.put(ormConfigPojo.getKey(), (String) ormConfigPojo.getValue());
            } else if (ormConfigPojo.getFieldType() == FieldType.BLOB) {
                contentValues.put(ormConfigPojo.getKey(), (Byte) ormConfigPojo.getValue());
            }
        }
        return contentValues;
    }

    private boolean isTablePresent(DbHelper dbHelper, String tableName) {
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'";
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(query, null);
        if (!AndroUtils.isNull(cursor) && cursor.getCount() != 0) {
            return true;
        } else {
            return false;
        }
    }


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
