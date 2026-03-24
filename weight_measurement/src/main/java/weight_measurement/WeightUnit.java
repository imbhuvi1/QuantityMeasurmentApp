package weight_measurement;

public enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    TON(1000.0);

    private final double factor;

    WeightUnit(double factor) {
        this.factor = factor;
    }

    // Convert to base unit (KG)
    public double toBase(double value) {
        return value * factor;
    }

    // Convert from base (KG) to target unit
    public double fromBase(double baseValue) {
        return baseValue / factor;
    }
}