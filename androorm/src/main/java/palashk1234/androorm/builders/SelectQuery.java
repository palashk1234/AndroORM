package palashk1234.androorm.builders;


import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import palashk1234.androorm.annotations.Column;
import palashk1234.androorm.annotations.Table;
import palashk1234.androorm.engine.DbEngine;
import palashk1234.androorm.models.OrmConfigModel;
import palashk1234.androorm.utils.AndroUtils;

/**
 * Created by Palash on 16 Feb 2017.
 * <p>
 * This is a class for Selection query.
 * This class contains a builder for building the query.
 */
public class SelectQuery {

    private static final String TAG = "SelectQuery";
    private String tableName;
    private String fields = null;
    private Class<?> clazz;
    private List<OrmConfigModel> lstOrmConfigModel;
    private String where = null;

    /**
     * Constructor for SelectQuery.
     *
     * @param builder Builder to construct the SelectQuery instance
     */
    private SelectQuery(Builder builder) {
        this.tableName = builder.tableName;
        this.fields = builder.fields;
        this.clazz = builder.clazz;
        this.lstOrmConfigModel = builder.lstOrmConfigModel;
        this.where = builder.where;
    }

    /**
     * Method to execute the Query
     *
     * @return List of data collected in selection query.
     */
    public List execute() {
        List<?> lstData = new ArrayList<>();
        if (!AndroUtils.isNullString(tableName)) {
            if (!AndroUtils.isNull(DbEngine.getInstance())) {
                lstData = DbEngine.getInstance().processSelectQuery(tableName, clazz, fields, where, lstOrmConfigModel);
            } else {
                Log.e(TAG, "DbEngine is Not running." +
                        " Please start the DbEngine using DbEngine.start(context,databaseName) " +
                        "in Application class");
            }
        }
        return lstData;
    }

    /**
     * Created by Palash on 16 Feb 2017.
     * <p>
     * This is a Builder class for Selection query.
     */
    public static class Builder {
        private String tableName;
        private Class<?> clazz;
        private String fields = null;
        private List<OrmConfigModel> lstOrmConfigModel = null;
        private String where = null;

        /**
         * Constructor for Builder class
         *
         * @param clazz type of class for which the query is to be built.
         */
        public Builder(Class<?> clazz) {
            this.clazz = clazz;
            processObject();
        }

        /**
         * Set type of class for which the query is to be built.
         *
         * @param clazz type of class for which the query is to be built.
         * @return Builder.
         */
        public Builder setClass(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        /**
         * Method for setting Selection fields.
         *
         * @param fieldsArray Array of fields.
         * @return Builder.
         */
        public Builder setFields(String... fieldsArray) {
            if (!AndroUtils.isNull(fieldsArray) && fieldsArray.length != 0) {
                String fields = "";
                for (String field : fieldsArray) {
                    fields = fields + field + ",";
                }

                fields.trim();
                StringBuilder builder = new StringBuilder(fields);
                builder.deleteCharAt(fields.length() - 1);
                fields = builder.toString();
                this.fields = fields;
            }
            return this;
        }

        /**
         * Set where clause for the select query.
         *
         * @param where where clause in String.
         * @return Builder.
         */
        public Builder where(String where) {
            if (where.toLowerCase().startsWith(" where ")) {
                this.where = where;
            } else {
                this.where = " where " + where;
            }
            return this;
        }

        /**
         * Method to Build Select query
         *
         * @return Instance of Select Query.
         */
        public SelectQuery build() {
            return new SelectQuery(Builder.this);
        }


        /**
         * Method for generating ORM Configuration Model for the provided class.
         */
        private void processObject() {
            if (clazz.isAnnotationPresent(Table.class)) {
                Annotation annotation = clazz.getAnnotation(Table.class);
                Table table = (Table) annotation;
                if (!AndroUtils.isNull(table)) {
                    this.tableName = table.name();

                    Field[] fields = clazz.getDeclaredFields();
                    if (!AndroUtils.isNull(fields) && fields.length != 0) {
                        lstOrmConfigModel = new ArrayList<>();
                        for (Field field : fields) {
                            annotation = field.getAnnotation(Column.class);
                            Column column = (Column) annotation;

                            lstOrmConfigModel.add(new OrmConfigModel(column.name(),
                                    column.fieldType(), column.fieldSize(),
                                    column.isPrimaryKey(), null));
                        }
                    }
                }


            }
        }


    }

}
