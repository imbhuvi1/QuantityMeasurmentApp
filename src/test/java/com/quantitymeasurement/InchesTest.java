package com.quantitymeasurement;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.Inches;

import static org.junit.jupiter.api.Assertions.*;

public class InchesTest {

    @Test
    void givenSameInches_shouldReturnTrue() {
        assertTrue(new Inches(12.0).equals(new Inches(12.0)));
    }

    @Test
    void givenDifferentInches_shouldReturnFalse() {
        assertFalse(new Inches(12.0).equals(new Inches(10.0)));
    }
}