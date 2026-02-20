package generic_length;


import org.junit.jupiter.api.Test;

import generic_length.QuantityMeasurementApp;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {

    /* ===========================
       FEET TEST CASES
    =========================== */

    @Test
    void testEquality_SameValue_Feet() {
        assertTrue(QuantityMeasurementApp.compareFeet(1.0, 1.0));
    }

    @Test
    void testEquality_DifferentValue_Feet() {
        assertFalse(QuantityMeasurementApp.compareFeet(1.0, 2.0));
    }

    @Test
    void testEquality_SameReference_Feet() {
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        assertTrue(feet.equals(feet));
    }

    @Test
    void testEquality_NullComparison_Feet() {
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(feet.equals(null));
    }

    @Test
    void testEquality_NonNumericInput_Feet() {
        QuantityMeasurementApp.Feet feet = new QuantityMeasurementApp.Feet(1.0);
        assertFalse(feet.equals("abc"));
    }

    /* ===========================
       INCHES TEST CASES
    =========================== */

    @Test
    void testEquality_SameValue_Inches() {
        assertTrue(QuantityMeasurementApp.compareInches(1.0, 1.0));
    }

    @Test
    void testEquality_DifferentValue_Inches() {
        assertFalse(QuantityMeasurementApp.compareInches(1.0, 2.0));
    }

    @Test
    void testEquality_SameReference_Inches() {
        QuantityMeasurementApp.Inches inches = new QuantityMeasurementApp.Inches(1.0);
        assertTrue(inches.equals(inches));
    }

    @Test
    void testEquality_NullComparison_Inches() {
        QuantityMeasurementApp.Inches inches = new QuantityMeasurementApp.Inches(1.0);
        assertFalse(inches.equals(null));
    }

    @Test
    void testEquality_NonNumericInput_Inches() {
        QuantityMeasurementApp.Inches inches = new QuantityMeasurementApp.Inches(1.0);
        assertFalse(inches.equals("xyz"));
    }
}