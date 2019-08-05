package com.mybank.entity.carddate;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DiscriminatorType;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;

public class CreditCardDateType
        extends AbstractSingleColumnStandardBasicType<CreditCardDate>
        implements DiscriminatorType<CreditCardDate> {

    public static final CreditCardDateType INSTANCE = new CreditCardDateType();

    public CreditCardDateType() {
        super(VarcharTypeDescriptor.INSTANCE, CreditCardDateTypeDescriptor.INSTANCE);
    }

    @Override
    public String getName() {
        return "card_date";
    }

    @Override
    public CreditCardDate stringToObject(String string) throws Exception {
        return fromString(string);
    }

    @Override
    public String objectToSQLString(CreditCardDate value, Dialect dlct) throws Exception {
        return toString(value);
    }
}
