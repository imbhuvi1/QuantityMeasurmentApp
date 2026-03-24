package addition_target;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

    @Test
    void givenFeetAndInches_shouldReturnInTargetUnit() {
        QuantityLength q1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, Unit.INCHES);

        QuantityLength result = q1.add(q2, Unit.INCHES);

        assertEquals(24.0, result.convertTo(Unit.INCHES));
    }
}