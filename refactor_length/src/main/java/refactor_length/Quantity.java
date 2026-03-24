package refactor_length;

public class Quantity {

    private final double value;
    private final Unit unit;

    public Quantity(double value, Unit unit) {
        this.value = value;
        this.unit = unit;
    }

    // Convert to another unit
    public double convertTo(Unit targetUnit) {
        double base = unit.toBase(value);
        return targetUnit.fromBase(base);
    }

    // Add two quantities and return in target unit
    public Quantity add(Quantity other, Unit targetUnit) {

        double base1 = this.unit.toBase(this.value);
        double base2 = other.unit.toBase(other.value);

        double sumBase = base1 + base2;

        double result = targetUnit.fromBase(sumBase);

        return new Quantity(result, targetUnit);
    }

    // Equality check
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        Quantity other = (Quantity) obj;

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        return Double.compare(thisBase, otherBase) == 0;
    }
}