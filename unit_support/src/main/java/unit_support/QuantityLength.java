package unit_support;

public class QuantityLength {

    private final double value;
    private final Unit unit;

    public QuantityLength(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        double thisValue = this.unit.toBaseUnit(this.value);
        double otherValue = other.unit.toBaseUnit(other.value);

        return Double.compare(thisValue, otherValue) == 0;
    }
}