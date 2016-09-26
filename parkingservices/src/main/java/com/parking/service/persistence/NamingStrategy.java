package com.parking.service.persistence;



import org.hibernate.AssertionFailure;
import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.internal.util.StringHelper;


public class NamingStrategy extends ImprovedNamingStrategy {

    private static final long serialVersionUID = 2199153102715260021L;

    @Override
    public String tableName(String tableName) {
        return tableName;
    }

    @Override
    public String columnName(String columnName) {
        return columnName;
    }


    @Override
    public String joinKeyColumnName(String joinedColumn, String joinedTable) {
        return super.columnName( joinedColumn );
    }

    @Override
    public String foreignKeyColumnName(
            String propertyName, String propertyEntityName, String propertyTableName, String referencedColumnName) {
        String header = propertyName != null ? StringHelper.unqualify(propertyName) : propertyTableName;
        if (header == null) throw new AssertionFailure("NamingStrategy not properly filled");
        return super.columnName( header ); //+ "_" + referencedColumnName not used for backward compatibility
    }
}
