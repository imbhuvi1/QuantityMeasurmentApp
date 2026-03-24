package unit_conversion;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityLengthTest {

    @Test
    void givenFeet_shouldConvertToInches() {
        QuantityLength q = new QuantityLength(1.0, Unit.FEET);
        assertEquals(12.0, q.convertTo(Unit.INCHES));
    }

    @Test
    void givenFeet_shouldConvertToYard() {
        QuantityLength q = new QuantityLength(3.0, Unit.FEET);
        assertEquals(1.0, q.convertTo(Unit.YARD));
    }

    @Test
    void givenCentimeter_shouldConvertToFeet() {
        QuantityLength q = new QuantityLength(30.48, Unit.CENTIMETER);
        assertEquals(1.0, q.convertTo(Unit.FEET), 0.01);
    }
}