package addition_target;

public enum Unit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.0328);

    private final double factor;

    Unit(double factor) {
        this.factor = factor;
    }

    // Convert to base unit (Feet)
    public double toBase(double value) {
        return value * factor;
    }

    // Convert from base unit (Feet) to target unit
    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}
