package refactor_length;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityTest {

    @Test
    void givenFeetAndInches_shouldBeEqual() {
        Quantity q1 = new Quantity(1.0, Unit.FEET);
        Quantity q2 = new Quantity(12.0, Unit.INCHES);

        assertTrue(q1.equals(q2));
    }

    @Test
    void givenDifferentValues_shouldNotBeEqual() {
        Quantity q1 = new Quantity(1.0, Unit.FEET);
        Quantity q2 = new Quantity(2.0, Unit.FEET);

        assertFalse(q1.equals(q2));
    }

    @Test
    void givenAddition_shouldReturnCorrectResult() {
        Quantity q1 = new Quantity(1.0, Unit.FEET);
        Quantity q2 = new Quantity(12.0, Unit.INCHES);

        Quantity result = q1.add(q2, Unit.INCHES);

        assertEquals(24.0, result.convertTo(Unit.INCHES));
    }

    @Test
    void givenConversion_shouldWorkCorrectly() {
        Quantity q = new Quantity(1.0, Unit.FEET);

        assertEquals(12.0, q.convertTo(Unit.INCHES));
    }
}