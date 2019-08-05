package com.mybank.entity.date;

import org.hibernate.engine.jdbc.env.spi.SQLStateType;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.SQLType;
import java.sql.Types;

public class DateTypeDescriptor extends AbstractTypeDescriptor<Date> {
    public static final DateTypeDescriptor INSTANCE =
            new DateTypeDescriptor();

    public DateTypeDescriptor() { //todo private
        super(Date.class);
    }

    @Override
    public String toString(Date value) {
        return value.toString();
    }

    @Override
    public Date fromString(String string) {
        return new Date(string);
    }

    @Override
    public <X> X unwrap(Date value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (Date.class.isAssignableFrom(type)) {
            return (X) value;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> Date wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isInstance(value)) {
            return fromString((String) value);
        }
        if (Date.class.isInstance(value)) {
            return (Date) value;
        }
        throw unknownWrap(value.getClass());
    }

    @Override
    public SqlTypeDescriptor getJdbcRecommendedSqlType(JdbcRecommendedSqlTypeMappingContext context) {
        return context.getTypeConfiguration().getSqlTypeDescriptorRegistry().getDescriptor( Types.VARCHAR );
    }
}
