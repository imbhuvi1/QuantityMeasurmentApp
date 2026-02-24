package unit_conversion;

//JUnit 5 test cases for QuantityLength conversions (UC5).

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

    private static final double EPSILON = 1e-6;

    @Test
    public void testConversion_FeetToInches() {
        double result = QuantityLength.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(12.0, result, EPSILON);
    }

    @Test
    public void testConversion_InchesToFeet() {
        double result = QuantityLength.convert(24.0, LengthUnit.INCHES, LengthUnit.FEET);
        assertEquals(2.0, result, EPSILON);
    }

    @Test
    public void testConversion_YardsToInches() {
        double result = QuantityLength.convert(1.0, LengthUnit.YARDS, LengthUnit.INCHES);
        assertEquals(36.0, result, EPSILON);
    }

    @Test
    public void testConversion_InchesToYards() {
        double result = QuantityLength.convert(72.0, LengthUnit.INCHES, LengthUnit.YARDS);
        assertEquals(2.0, result, EPSILON);
    }

    @Test
    public void testConversion_CentimetersToInches() {
        double result = QuantityLength.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES);
        assertEquals(1.0, result, EPSILON);
    }

    @Test
    public void testConversion_FeetToYards() {
        double result = QuantityLength.convert(6.0, LengthUnit.FEET, LengthUnit.YARDS);
        assertEquals(2.0, result, EPSILON);
    }

    @Test
    public void testConversion_RoundTrip_PreservesValue() {
        double value = 5.0;
        double converted = QuantityLength.convert(value, LengthUnit.FEET, LengthUnit.INCHES);
        double roundTrip = QuantityLength.convert(converted, LengthUnit.INCHES, LengthUnit.FEET);
        assertEquals(value, roundTrip, EPSILON);
    }

    @Test
    public void testConversion_ZeroValue() {
        double result = QuantityLength.convert(0.0, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(0.0, result, EPSILON);
    }

    @Test
    public void testConversion_NegativeValue() {
        double result = QuantityLength.convert(-1.0, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(-12.0, result, EPSILON);
    }

    @Test
    public void testConversion_SameUnit() {
        double result = QuantityLength.convert(5.0, LengthUnit.FEET, LengthUnit.FEET);
        assertEquals(5.0, result, EPSILON);
    }

    @Test
    public void testConversion_InvalidUnit_NullSource() {
        assertThrows(IllegalArgumentException.class, () -> {
            QuantityLength.convert(1.0, null, LengthUnit.INCHES);
        });
    }

    @Test
    public void testConversion_InvalidUnit_NullTarget() {
        assertThrows(IllegalArgumentException.class, () -> {
            QuantityLength.convert(1.0, LengthUnit.FEET, null);
        });
    }

    @Test
    public void testConversion_InvalidValue_NaN() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(Double.NaN, LengthUnit.FEET);
        });
    }

    @Test
    public void testConversion_InvalidValue_Infinite() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(Double.POSITIVE_INFINITY, LengthUnit.FEET);
        });
    }

    @Test
    public void testConversion_LargeValue() {
        double result = QuantityLength.convert(1e6, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(1.2e7, result, EPSILON);
    }

    @Test
    public void testConversion_SmallValue() {
        double result = QuantityLength.convert(1e-6, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(1e-6 * 12.0, result, EPSILON);
    }
}