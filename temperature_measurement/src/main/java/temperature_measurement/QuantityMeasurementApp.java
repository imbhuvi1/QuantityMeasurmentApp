package temperature_measurement;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityTemperature t1 = new QuantityTemperature(0.0, TemperatureUnit.CELSIUS);
        QuantityTemperature t2 = new QuantityTemperature(32.0, TemperatureUnit.FAHRENHEIT);

        System.out.println("Equality: " + t1.equals(t2));

        System.out.println("0°C in Fahrenheit = " + t1.convertTo(TemperatureUnit.FAHRENHEIT));
    }
}