package dev.t1dmlgus.common.util;

import javax.persistence.AttributeConverter;

public class MoneyConverter implements AttributeConverter<Money, Double> {


    @Override
    public Double convertToDatabaseColumn(Money money) {
        return money == null ? null : money.getValue();
    }

    @Override
    public Money convertToEntityAttribute(Double value) {
        return value == null ? null : new Money(value);
    }
}
