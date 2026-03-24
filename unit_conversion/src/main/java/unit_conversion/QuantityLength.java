package unit_conversion;

public class QuantityLength {

    private final double value;
    private final Unit unit;

    public QuantityLength(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double convertTo(Unit targetUnit) {
        double base = unit.toBase(value);
        return targetUnit.fromBase(base);
    }
}