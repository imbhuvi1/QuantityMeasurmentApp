package generic_length;

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

        double thisValueInFeet = this.unit.toBaseUnit(this.value);
        double otherValueInFeet = other.unit.toBaseUnit(other.value);

        return Double.compare(thisValueInFeet, otherValueInFeet) == 0;
    }
}