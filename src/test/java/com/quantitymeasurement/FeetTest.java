package com.quantitymeasurement;

import org.junit.jupiter.api.Test;

import com.quantitymeasurement.Feet;

import static org.junit.jupiter.api.Assertions.*;

public class FeetTest {

    @Test
    void givenSameFeet_shouldReturnTrue() {
        assertTrue(new Feet(1.0).equals(new Feet(1.0)));
    }

    @Test
    void givenDifferentFeet_shouldReturnFalse() {
        assertFalse(new Feet(1.0).equals(new Feet(2.0)));
    }
}