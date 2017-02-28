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
import palashk1234.androorm.models.OrmConfigModel;
import palashk1234.androorm.utils.AndroUtils;

/**
 * Created by Palash on 16 Feb 2017.
 * <p>
 * This is a class for Insertion query.
 * This class contains a builder for building the query.
 */
public class InsertQuery {

    private static final String TAG = "InsertQuery";
    private String tableName;
    private Object object;
    private List<OrmConfigModel> lstOrmConfigModel;

    /**
     * Constructor for InsertQuery.
     *
     * @param builder Builder to construct the InsertQuery instance
     */
    private InsertQuery(Builder builder) {
        this.tableName = builder.tableName;
        this.object = builder.object;
        this.lstOrmConfigModel = builder.lstOrmConfigModel;
    }

    /**
     * Method to execute the Query
     *
     * @return Boolean (Whether the data is inserted or not).
     */
    public boolean execute() {
        boolean isInsertSuccess = false;
        if (!AndroUtils.isNullString(tableName)) {
            if (!AndroUtils.isNull(DbEngine.getInstance())) {
                isInsertSuccess = DbEngine.getInstance().processInsertQuery(tableName, lstOrmConfigModel);
            } else {
                Log.e(TAG, "DbEngine is Not running." +
                        " Please start the DbEngine using DbEngine.start(context,databaseName) " +
                        "in Application class");
            }
        }
        return isInsertSuccess;
    }

    /**
     * Created by Palash on 16 Feb 2017.
     * <p>
     * This is a Builder class for Insertion query.
     */
    public static class Builder {
        private String tableName;
        private Object object;
        private List<OrmConfigModel> lstOrmConfigModel = null;


        /**
         * Constructor for Builder class
         *
         * @param object object of type class.
         */
        public Builder(Object object) {
            this.object = object;
            processObject();

        }

        /**
         * Set object of class for which the query is to be built.
         *
         * @param object object of class for which the query is to be built.
         * @return Builder.
         */
        public Builder setData(Object object) {
            this.object = object;
            return this;
        }


        /**
         * Method to Build Select query
         *
         * @return Instance of Select Query.
         */
        public InsertQuery build() {
            return new InsertQuery(Builder.this);
        }


        /**
         * Method for generating ORM Configuration Model for the provided class.
         */
        private void processObject() {
            Class clazz = object.getClass();
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
                            if (Modifier.isPublic(field.getModifiers())) {
                                try {
                                    lstOrmConfigModel.add(new OrmConfigModel(column.name(),
                                            column.fieldType(), column.fieldSize(),
                                            column.isPrimaryKey(), field.get(object)));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Method method[] = clazz.getDeclaredMethods();
                                for (Method meth : method) {

                                    if (meth.getName().startsWith("get")
                                            && meth.getName().length() == (field.getName().length() + 3)) {

                                        if (meth.getName().toLowerCase()
                                                .endsWith(field.getName().toLowerCase())) {
                                            // Method found
                                            try {
                                                lstOrmConfigModel.add(new OrmConfigModel(column.name(),
                                                        column.fieldType(), column.fieldSize(),
                                                        column.isPrimaryKey(), meth.invoke(object)));
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


    }

}
