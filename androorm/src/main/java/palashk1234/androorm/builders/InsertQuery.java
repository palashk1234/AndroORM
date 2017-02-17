package palashk1234.androorm.builders;

import android.util.Log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import palashk1234.androorm.annotations.Entity;
import palashk1234.androorm.annotations.Table;
import palashk1234.androorm.engine.DbEngine;
import palashk1234.androorm.models.OrmConfigPojo;
import palashk1234.androorm.utils.AndroUtils;

/**
 * Created by palash on 16 Feb 2017.
 */

public class InsertQuery {

    private static final String TAG = "InsertQuery";
    private String tableName;
    private Object object;
    private List<OrmConfigPojo> lstOrmConfigPojo;

    private InsertQuery(Builder builder) {
        this.tableName = builder.tableName;
        this.object = builder.object;
        this.lstOrmConfigPojo = builder.lstOrmConfigPojo;
    }

    public boolean execute() {
        boolean isInsertSuccess = false;
        if (!AndroUtils.isNullString(tableName)) {
            if (!AndroUtils.isNull(DbEngine.getInstance())) {
                isInsertSuccess = DbEngine.getInstance().processInsertQuery(tableName, lstOrmConfigPojo);
            } else {
                Log.e(TAG, "DbEngine is Not running." +
                        " Please start the DbEngine using DbEngine.start(context,databaseName) " +
                        "in Application class");
            }
        }
        return isInsertSuccess;
    }

    public static class Builder {
        private String tableName;
        private Object object;
        private List<OrmConfigPojo> lstOrmConfigPojo = null;

        public Builder(Object object) {
            this.object = object;
            processObject();

        }

        public InsertQuery build() {
            return new InsertQuery(Builder.this);
        }


        private void processObject() {
            Class clazz = object.getClass();
            if (clazz.isAnnotationPresent(Table.class)) {
                Annotation annotation = clazz.getAnnotation(Table.class);
                Table table = (Table) annotation;
                this.tableName = table.TableName();

                Field[] fields = clazz.getDeclaredFields();
                if (!AndroUtils.isNull(fields) && fields.length != 0) {
                    lstOrmConfigPojo = new ArrayList<>();
                    for (Field field : fields) {
                        annotation = field.getAnnotation(Entity.class);
                        Entity entity = (Entity) annotation;

                        Method method[] = clazz.getDeclaredMethods();
                        for (Method meth : method) {

                            if (meth.getName().startsWith("get")
                                    && meth.getName().length() == (field.getName().length() + 3)) {

                                if (meth.getName().toLowerCase()
                                        .endsWith(field.getName().toLowerCase())) {
                                    // Method found
                                    try {
                                        lstOrmConfigPojo.add(new OrmConfigPojo(entity.FieldName(),
                                                entity.FieldType(), entity.FieldSize(),
                                                entity.isPrimaryKey(), meth.invoke(object)));
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


    }

}
