package unit_addition;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

    @Test
    void givenFeetAndInches_shouldAddCorrectly() {
        QuantityLength q1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, Unit.INCHES);

        QuantityLength result = q1.add(q2);

        assertEquals(2.0, result.convertTo(Unit.FEET));
    }
}