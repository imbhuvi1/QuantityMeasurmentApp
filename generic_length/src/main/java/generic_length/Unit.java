package generic_length;

public enum Unit {

    FEET(1.0),
    INCHES(1.0 / 12.0);

    private final double conversionFactor;

    Unit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double toBaseUnit(double value) {
        return value * conversionFactor;
    }
}