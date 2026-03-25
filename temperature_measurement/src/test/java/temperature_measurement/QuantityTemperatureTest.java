package temperature_measurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityTemperatureTest {

    @Test
    void givenCelsiusAndFahrenheit_shouldBeEqual() {
        QuantityTemperature t1 = new QuantityTemperature(0.0, TemperatureUnit.CELSIUS);
        QuantityTemperature t2 = new QuantityTemperature(32.0, TemperatureUnit.FAHRENHEIT);

        assertTrue(t1.equals(t2));
    }

    @Test
    void givenConversion_shouldWork() {
        QuantityTemperature t = new QuantityTemperature(100.0, TemperatureUnit.CELSIUS);

        assertEquals(212.0, t.convertTo(TemperatureUnit.FAHRENHEIT));
    }
}