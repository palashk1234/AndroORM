package palashk1234.androorm.builders;


import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import palashk1234.androorm.annotations.Column;
import palashk1234.androorm.annotations.Table;
import palashk1234.androorm.engine.DbEngine;
import palashk1234.androorm.models.OrmConfigPojo;
import palashk1234.androorm.utils.AndroUtils;

/**
 * Created by palash on 16 Feb 2017.
 */

public class SelectQuery {

    private static final String TAG = "SelectQuery";
    private String tableName;
    private String fields = null;
    private Class<?> clazz;
    private List<OrmConfigPojo> lstOrmConfigPojo;

    private SelectQuery(Builder builder) {
        this.tableName = builder.tableName;
        this.fields = builder.fields;
        this.clazz = builder.clazz;
        this.lstOrmConfigPojo = builder.lstOrmConfigPojo;
    }

    public List execute() {
        List<?> lstData = null;
        if (!AndroUtils.isNullString(tableName)) {
            if (!AndroUtils.isNull(DbEngine.getInstance())) {
                lstData = DbEngine.getInstance().processSelectQuery(tableName, clazz,lstOrmConfigPojo);
            } else {
                Log.e(TAG, "DbEngine is Not running." +
                        " Please start the DbEngine using DbEngine.start(context,databaseName) " +
                        "in Application class");
            }
        }
        return lstData;
    }

    public static class Builder {
        private String tableName;
        private Class<?> clazz;
        private String fields = null;
        private List<OrmConfigPojo> lstOrmConfigPojo = null;

        public Builder(Class<?> clazz) {
            this.clazz = clazz;
            processObject();
        }

        public Builder setClass(Class<?> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder setFields(String fields) {
            this.fields = fields;
            return this;
        }

        public SelectQuery build() {
            return new SelectQuery(Builder.this);
        }


        private void processObject() {
            if (clazz.isAnnotationPresent(Table.class)) {
                Annotation annotation = clazz.getAnnotation(Table.class);
                Table table = (Table) annotation;
                this.tableName = table.name();

                Field[] fields = clazz.getDeclaredFields();
                if (!AndroUtils.isNull(fields) && fields.length != 0) {
                    lstOrmConfigPojo = new ArrayList<>();
                    for (Field field : fields) {
                        annotation = field.getAnnotation(Column.class);
                        Column column = (Column) annotation;
                        if (Modifier.isPublic(field.getModifiers())) {
                            try {
                                lstOrmConfigPojo.add(new OrmConfigPojo(column.name(),
                                        column.fieldType(), column.fieldSize(),
                                        column.isPrimaryKey(), null));
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
