package unit_support;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

    @Test
    void givenFeetAndInches_shouldReturnTrue() {
        assertTrue(new QuantityLength(1.0, Unit.FEET)
                .equals(new QuantityLength(12.0, Unit.INCHES)));
    }

    @Test
    void givenFeetAndYard_shouldReturnTrue() {
        assertTrue(new QuantityLength(3.0, Unit.FEET)
                .equals(new QuantityLength(1.0, Unit.YARD)));
    }

    @Test
    void givenCmAndFeet_shouldReturnTrue() {
        assertTrue(new QuantityLength(30.48, Unit.CENTIMETER)
                .equals(new QuantityLength(1.0, Unit.FEET)));
    }

    @Test
    void givenDifferentValues_shouldReturnFalse() {
        assertFalse(new QuantityLength(1.0, Unit.FEET)
                .equals(new QuantityLength(2.0, Unit.FEET)));
    }
}