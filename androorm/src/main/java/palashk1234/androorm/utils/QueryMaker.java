package palashk1234.androorm.utils;

import android.database.Cursor;
import android.util.Log;

import java.util.List;

import palashk1234.androorm.models.OrmConfigPojo;

/**
 * Created by palash on 17 Feb 2017.
 */

public class QueryMaker {

    private static final String TAG = "QueryMaker";

    public static String makeCreateTableQuery(String tableName, List<OrmConfigPojo> lstOrmConfigPojo) {
        String query = "";
        if (!AndroUtils.isNull(tableName)) {
            if (!AndroUtils.isNull(lstOrmConfigPojo) && !lstOrmConfigPojo.isEmpty()) {
                query = "CREATE TABLE " + tableName + " (";
                for (OrmConfigPojo ormConfigPojo : lstOrmConfigPojo) {
                    if (!AndroUtils.isNullString(ormConfigPojo.getKey())) {
                        if (ormConfigPojo.getFieldType() == FieldType.INTEGER) {
                            query = query + ormConfigPojo.getKey() + " INTEGER ";
                            if (ormConfigPojo.getFieldSize() != 0) {
                                query = query + "(" + ormConfigPojo.getFieldSize() + ") ";
                            }
                            if (ormConfigPojo.isPrimaryKey()) {
                                query = query + "PRIMARY KEY ";
                            }
                            query = query + ", ";
                        } else if (ormConfigPojo.getFieldType() == FieldType.REAL) {
                            query = query + ormConfigPojo.getKey() + " REAL ";
                            if (ormConfigPojo.getFieldSize() != 0) {
                                query = query + "(" + ormConfigPojo.getFieldSize() + ") ";
                            }
                            if (ormConfigPojo.isPrimaryKey()) {
                                query = query + "PRIMARY KEY ";
                            }
                            query = query + ", ";

                        } else if (ormConfigPojo.getFieldType() == FieldType.TEXT) {
                            query = query + ormConfigPojo.getKey() + " TEXT ";
                            if (ormConfigPojo.getFieldSize() != 0) {
                                query = query + "(" + ormConfigPojo.getFieldSize() + ") ";
                            }
                            if (ormConfigPojo.isPrimaryKey()) {
                                query = query + "PRIMARY KEY ";
                            }
                            query = query + ", ";
                        } else if (ormConfigPojo.getFieldType() == FieldType.BLOB) {
                            query = query + ormConfigPojo.getKey() + " BLOB ";
                            if (ormConfigPojo.getFieldSize() != 0) {
                                query = query + "(" + ormConfigPojo.getFieldSize() + ") ";
                            }
                            if (ormConfigPojo.isPrimaryKey()) {
                                query = query + "PRIMARY KEY ";
                            }
                            query = query + ", ";
                        }
                    } else {
                        Log.e(TAG, "Field name should not be empty. Please check all the field names in " + tableName);
                        query = "";
                        return query;
                    }
                }
                query = query.trim();
                StringBuilder stringBuilder = new StringBuilder(query);
                stringBuilder.deleteCharAt(query.length() -1 );
                query = stringBuilder.toString();
                query = query + ")";
                Log.e(TAG, "query - " + query);
            }
        }
        return query;
    }
}
