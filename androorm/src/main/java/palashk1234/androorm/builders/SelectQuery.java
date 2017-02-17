package palashk1234.androorm.builders;



import java.lang.annotation.Annotation;

import palashk1234.androorm.annotations.Table;
import palashk1234.androorm.utils.AndroUtils;

/**
 * Created by palash on 16 Feb 2017.
 */

public class SelectQuery {

    private String tableName;
    private String fields = null;
    private Class<?> clazz;

    private SelectQuery(Builder builder) {
        this.tableName = builder.tableName;
        this.fields = builder.fields;
        this.clazz = builder.clazz;
    }

    public String execute() {
        String query = "";
        if (!AndroUtils.isNullString(tableName)) {
            query = "SELECT * FROM " + tableName;
            if (!AndroUtils.isNullString(fields)) {
                query = "SELECT " + fields + " FROM " + tableName;
            }
        }
        return query;
    }

    public static class Builder {
        private String tableName;
        private Class<?> clazz;
        private String fields = null;

        public Builder(Class<?> clazz) {
            this.clazz = clazz;
            if (clazz.isAnnotationPresent(Table.class)) {
                Annotation annotation = clazz.getAnnotation(Table.class);
                Table table = (Table) annotation;
                this.tableName = table.name();
            }
        }

        public Builder setFields(String fields) {
            this.fields = fields;
            return this;
        }

        public SelectQuery build() {
            return new SelectQuery(Builder.this);
        }

    }

}
