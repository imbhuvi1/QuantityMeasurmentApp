package unit_conversion;

public class QuantityMeasurementApp {

    // Overloaded method 1: raw values
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double converted = QuantityLength.convert(value, from, to);
        System.out.printf("%.6f %s = %.6f %s%n", value, from, converted, to);
    }

    // Overloaded method 2: existing QuantityLength instance
    public static void demonstrateLengthConversion(QuantityLength length, LengthUnit to) {
        double converted = length.convertTo(to);
        System.out.printf("%.6f %s = %.6f %s%n", length.getValue(), length.getUnit(), converted, to);
    }

    public static void main(String[] args) {
        // Examples
        demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        demonstrateLengthConversion(3.0, LengthUnit.YARDS, LengthUnit.FEET);
        demonstrateLengthConversion(36.0, LengthUnit.INCHES, LengthUnit.YARDS);
        demonstrateLengthConversion(1.0, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
        demonstrateLengthConversion(0.0, LengthUnit.FEET, LengthUnit.INCHES);

        // Using QuantityLength instance
        QuantityLength lengthInYards = new QuantityLength(5.0, LengthUnit.YARDS);
        demonstrateLengthConversion(lengthInYards, LengthUnit.INCHES);
    }
}