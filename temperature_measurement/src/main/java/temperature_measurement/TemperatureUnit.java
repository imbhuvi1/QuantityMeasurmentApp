package temperature_measurement;

public enum TemperatureUnit {

    CELSIUS,
    FAHRENHEIT;

    public double toBase(double value) {
        // Base unit = Celsius
        if (this == FAHRENHEIT) {
            return (value - 32) * 5 / 9;
        }
        return value;
    }

    public double fromBase(double baseValue) {
        if (this == FAHRENHEIT) {
            return (baseValue * 9 / 5) + 32;
        }
        return baseValue;
    }
}