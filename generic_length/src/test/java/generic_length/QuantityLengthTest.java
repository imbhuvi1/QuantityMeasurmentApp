package generic_length;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

    @Test
    void givenSameFeet_shouldReturnTrue() {
        assertTrue(new QuantityLength(1.0, Unit.FEET)
                .equals(new QuantityLength(1.0, Unit.FEET)));
    }

    @Test
    void givenFeetAndInches_shouldReturnTrue() {
        assertTrue(new QuantityLength(1.0, Unit.FEET)
                .equals(new QuantityLength(12.0, Unit.INCHES)));
    }

    @Test
    void givenDifferentValues_shouldReturnFalse() {
        assertFalse(new QuantityLength(1.0, Unit.FEET)
                .equals(new QuantityLength(2.0, Unit.FEET)));
    }

    @Test
    void givenNull_shouldReturnFalse() {
        assertFalse(new QuantityLength(1.0, Unit.FEET).equals(null));
    }

    @Test
    void givenDifferentType_shouldReturnFalse() {
        assertFalse(new QuantityLength(1.0, Unit.FEET).equals("test"));
    }
}