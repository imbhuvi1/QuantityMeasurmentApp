package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.unit.WeightUnit;

public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    private static final double EPSILON = 0.00001;

    public QuantityWeight(double value, WeightUnit unit) {

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public QuantityWeight convertTo(WeightUnit targetUnit) {

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double base = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(base);

        return new QuantityWeight(converted, targetUnit);
    }

    public QuantityWeight add(QuantityWeight other) {

        if (other == null)
            throw new IllegalArgumentException("Other weight cannot be null");

        double sum = this.toBaseUnit() + other.toBaseUnit();

        double result = unit.convertFromBaseUnit(sum);

        return new QuantityWeight(result, unit);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {

        if (other == null)
            throw new IllegalArgumentException("Other weight cannot be null");

        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit cannot be null");

        double sum = this.toBaseUnit() + other.toBaseUnit();

        double result = targetUnit.convertFromBaseUnit(sum);

        return new QuantityWeight(result, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass())
            return false;

        QuantityWeight other = (QuantityWeight) obj;
        
        return Math.abs(
                this.toBaseUnit() - other.toBaseUnit()
        ) < EPSILON;
    }

    @Override
    public int hashCode() {

        long normalized = Math.round(toBaseUnit() / EPSILON);

        return Long.hashCode(normalized);
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit + ")";
    }
}
