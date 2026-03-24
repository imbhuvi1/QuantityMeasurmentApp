package addition_target;

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

    // UC7 METHOD
    public QuantityLength add(QuantityLength other, Unit targetUnit) {

        double base1 = this.unit.toBase(this.value);
        double base2 = other.unit.toBase(other.value);

        double sumBase = base1 + base2;

        double resultValue = targetUnit.fromBase(sumBase);

        return new QuantityLength(resultValue, targetUnit);
    }
}