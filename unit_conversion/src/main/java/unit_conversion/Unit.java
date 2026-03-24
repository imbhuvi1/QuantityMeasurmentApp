package unit_conversion;

public enum Unit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.0328);

    private final double factor;

    Unit(double factor) {
        this.factor = factor;
    }

    public double toBase(double value) {
        return value * factor;
    }

    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}