package unit_conversion;

//Enum representing supported length units with conversion factors relative to FEET as base unit.

public enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),   // 1 inch = 1/12 feet
    YARDS(3.0),            // 1 yard = 3 feet
    CENTIMETERS(0.0328084); // 1 cm ≈ 0.0328084 feet

    private final double conversionFactor; // relative to base unit FEET

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }
}