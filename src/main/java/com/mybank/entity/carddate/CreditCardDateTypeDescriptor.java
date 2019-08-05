package com.mybank.entity.carddate;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.spi.JdbcRecommendedSqlTypeMappingContext;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;

import java.sql.Types;

public class CreditCardDateTypeDescriptor extends AbstractTypeDescriptor<CreditCardDate> {
    public static final CreditCardDateTypeDescriptor INSTANCE =
            new CreditCardDateTypeDescriptor();

    public CreditCardDateTypeDescriptor() { //todo private
        super(CreditCardDate.class);
    }

    @Override
    public String toString(CreditCardDate value) {
        return value.toString();
    }

    @Override
    public CreditCardDate fromString(String string) {
        return new CreditCardDate(string);
    }

    @Override
    public <X> X unwrap(CreditCardDate value, Class<X> type, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (CreditCardDate.class.isAssignableFrom(type)) {
            return (X) value;
        }
        if (String.class.isAssignableFrom(type)) {
            return (X) toString(value);
        }
        throw unknownUnwrap(type);
    }

    @Override
    public <X> CreditCardDate wrap(X value, WrapperOptions options) {
        if (value == null) {
            return null;
        }
        if (String.class.isInstance(value)) {
            return fromString((String) value);
        }
        if (CreditCardDate.class.isInstance(value)) {
            return (CreditCardDate) value;
        }
        throw unknownWrap(value.getClass());
    }

    @Override
    public SqlTypeDescriptor getJdbcRecommendedSqlType(JdbcRecommendedSqlTypeMappingContext context) {
        return context.getTypeConfiguration().getSqlTypeDescriptorRegistry().getDescriptor( Types.VARCHAR );
    }
}
