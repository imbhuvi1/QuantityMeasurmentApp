package unit_support;

public enum Unit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(0.0328);

    private final double conversionFactor;

    Unit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double toBaseUnit(double value) {
        return value * conversionFactor;
    }
}