package com.mybank.entity.date;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class DateType
        extends AbstractSingleColumnStandardBasicType<Date>
        implements DiscriminatorType<Date> {

    public static final DateType INSTANCE = new DateType();

    public DateType() {
        super(VarcharTypeDescriptor.INSTANCE, DateTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "date";
    }

    @Override
    public Date stringToObject(String string) throws Exception {
        return fromString(string);
    }

    @Override
    public String objectToSQLString(Date value, Dialect dlct) throws Exception {
        return toString(value);
    }
}
