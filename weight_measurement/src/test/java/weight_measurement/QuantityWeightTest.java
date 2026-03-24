package weight_measurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityWeightTest {

    @Test
    void givenKgAndGram_shouldBeEqual() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        assertTrue(w1.equals(w2));
    }

    @Test
    void givenAddition_shouldReturnCorrectResult() {
        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(500.0, WeightUnit.GRAM);

        QuantityWeight result = w1.add(w2, WeightUnit.KILOGRAM);

        assertEquals(1.5, result.convertTo(WeightUnit.KILOGRAM));
    }

    @Test
    void givenConversion_shouldWork() {
        QuantityWeight w = new QuantityWeight(1.0, WeightUnit.TON);

        assertEquals(1000.0, w.convertTo(WeightUnit.KILOGRAM));
    }
}