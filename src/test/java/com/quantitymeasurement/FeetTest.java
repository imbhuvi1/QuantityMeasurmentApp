package com.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeetTest {

    @Test
    void givenSameFeetValues_shouldReturnTrue() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        assertTrue(f1.equals(f2));
    }

    @Test
    void givenDifferentFeetValues_shouldReturnFalse() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(2.0);

        assertFalse(f1.equals(f2));
    }

    @Test
    void givenNull_shouldReturnFalse() {
        Feet f1 = new Feet(1.0);

        assertFalse(f1.equals(null));
    }

    @Test
    void givenSameReference_shouldReturnTrue() {
        Feet f1 = new Feet(1.0);

        assertTrue(f1.equals(f1));
    }

    @Test
    void givenDifferentType_shouldReturnFalse() {
        Feet f1 = new Feet(1.0);

        assertFalse(f1.equals("test"));
    }
}