package temperature_measurement;

public class QuantityTemperature {

    private final double value;
    private final TemperatureUnit unit;

    public QuantityTemperature(double value, TemperatureUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double convertTo(TemperatureUnit targetUnit) {
        double base = unit.toBase(value);
        return targetUnit.fromBase(base);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityTemperature other = (QuantityTemperature) obj;

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        return Double.compare(thisBase, otherBase) == 0;
    }
}