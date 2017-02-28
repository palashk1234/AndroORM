package palashk1234.androorm.utils;

import android.util.Log;

import java.util.List;

import palashk1234.androorm.models.OrmConfigModel;

/**
 * Created by Palash on 17 Feb 2017.
 * <p>
 * This is the utility class for generating queries for SQL operations.
 */
public class QueryMaker {

    private static final String TAG = "QueryMaker";

    /**
     * Method for generating Query for Creating a Table.
     *
     * @param tableName        Name of Table
     * @param lstOrmConfigModel Configuration Model of the class (Auto Generated in the Builders).
     * @return
     */
    public static String makeCreateTableQuery(String tableName, List<OrmConfigModel> lstOrmConfigModel) {
        String query = "";
        if (!AndroUtils.isNull(tableName)) {
            if (!AndroUtils.isNull(lstOrmConfigModel) && !lstOrmConfigModel.isEmpty()) {
                query = "CREATE TABLE " + tableName + " (";
                for (OrmConfigModel ormConfigModel : lstOrmConfigModel) {
                    if (!AndroUtils.isNullString(ormConfigModel.getColumnName())) {
                        if (ormConfigModel.getFieldType() == FieldType.INTEGER) {
                            query = query + ormConfigModel.getColumnName() + " INTEGER ";
                            if (ormConfigModel.getFieldSize() != 0) {
                                query = query + "(" + ormConfigModel.getFieldSize() + ") ";
                            }
                            if (ormConfigModel.isPrimaryKey()) {
                                query = query + "PRIMARY KEY ";
                            }
                            query = query + ", ";
                        } else if (ormConfigModel.getFieldType() == FieldType.REAL) {
                            query = query + ormConfigModel.getColumnName() + " REAL ";
                            if (ormConfigModel.getFieldSize() != 0) {
                                query = query + "(" + ormConfigModel.getFieldSize() + ") ";
                            }
                            if (ormConfigModel.isPrimaryKey()) {
                                query = query + "PRIMARY KEY ";
                            }
                            query = query + ", ";

                        } else if (ormConfigModel.getFieldType() == FieldType.TEXT) {
                            query = query + ormConfigModel.getColumnName() + " TEXT ";
                            if (ormConfigModel.getFieldSize() != 0) {
                                query = query + "(" + ormConfigModel.getFieldSize() + ") ";
                            }
                            if (ormConfigModel.isPrimaryKey()) {
                                query = query + "PRIMARY KEY ";
                            }
                            query = query + ", ";
                        } else if (ormConfigModel.getFieldType() == FieldType.BLOB) {
                            query = query + ormConfigModel.getColumnName() + " BLOB ";
                            if (ormConfigModel.getFieldSize() != 0) {
                                query = query + "(" + ormConfigModel.getFieldSize() + ") ";
                            }
                            if (ormConfigModel.isPrimaryKey()) {
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
                stringBuilder.deleteCharAt(query.length() - 1);
                query = stringBuilder.toString();
                query = query + ")";
                Log.d(TAG, "query - " + query);
            }
        }
        return query;
    }

    /**
     * * Method for generating Selection  query from a table Table.
     *
     * @param tableName        Name of the table.
     * @param clazz            type of the class.
     * @param fields           Selection fields (Can be Null).
     * @param where            Where Clause (Can be Null).
     * @param lstOrmConfigModel Configuration Model of the class (Auto Generated in Builders).
     * @return
     */
    public static String makeSelectQuery(String tableName, Class<?> clazz, String fields, String where, List<OrmConfigModel> lstOrmConfigModel) {
        String query = "";
        if (!AndroUtils.isNullString(fields)) {
            query = "SELECT " + fields + " FROM " + tableName;
        } else {
            if (!AndroUtils.isNull(lstOrmConfigModel) && !lstOrmConfigModel.isEmpty()) {
                query = "SELECT ";
                for (OrmConfigModel ormConfigModel : lstOrmConfigModel) {
                    query = query + ormConfigModel.getColumnName() + ", ";
                }
                query = query.trim();
                StringBuilder stringBuilder = new StringBuilder(query);
                stringBuilder.deleteCharAt(query.length() - 1);
                query = stringBuilder.toString();
                query = query + " FROM " + tableName;
            } else {
                Log.e(TAG, "Query Making failed, Please check your class" + clazz.getName());
                return "";
            }
        }
        if (!AndroUtils.isNullString(query)) {
            if (!AndroUtils.isNullString(where)) {
                query = query + where;
            }
        }
        Log.d(TAG, "query - " + query);
        return query;
    }
}
