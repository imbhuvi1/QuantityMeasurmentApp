package unit_conversion;

//Class representing a length measurement. Immutable value object.

public class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null.");
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite.");
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    /* Converts this quantity to a target unit.
     * @param targetUnit target length unit
     * @return converted numeric value 
     */
    public double convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) throw new IllegalArgumentException("Target unit cannot be null.");
        // Normalize to base unit (FEET)
        double valueInBase = this.value * this.unit.getConversionFactor();
        // Convert to target unit
        return valueInBase / targetUnit.getConversionFactor();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength other)) return false;
        // Compare values after converting to base unit
        double epsilon = 1e-6;
        double thisInBase = this.value * this.unit.getConversionFactor();
        double otherInBase = other.value * other.unit.getConversionFactor();
        return Math.abs(thisInBase - otherInBase) < epsilon;
    }

    @Override
    public String toString() {
        return String.format("%.6f %s", value, unit);
    }

    /**
     * Static utility method for conversion without creating an instance.
     */
    public static double convert(double value, LengthUnit source, LengthUnit target) {
        return new QuantityLength(value, source).convertTo(target);
    }
}
