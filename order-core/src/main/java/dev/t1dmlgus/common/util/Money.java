package dev.t1dmlgus.common.util;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Money {

    private final double value;

    public Money(double value) {
        this.value = value;
    }

    public Money plus(Money money) {
        return new Money(this.value + money.value);
    }

    public Money multiply(double multiplier) {
        return new Money(value * multiplier);
    }

    public Money minus(Money money) {
        return new Money(value - money.value);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return value == money.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
