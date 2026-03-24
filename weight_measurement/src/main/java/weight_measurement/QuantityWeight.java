package weight_measurement;

public class QuantityWeight {

    private final double value;
    private final WeightUnit unit;

    public QuantityWeight(double value, WeightUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public double convertTo(WeightUnit targetUnit) {
        double base = unit.toBase(value);
        return targetUnit.fromBase(base);
    }

    public QuantityWeight add(QuantityWeight other, WeightUnit targetUnit) {

        double base1 = this.unit.toBase(this.value);
        double base2 = other.unit.toBase(other.value);

        double sum = base1 + base2;

        double result = targetUnit.fromBase(sum);

        return new QuantityWeight(result, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityWeight other = (QuantityWeight) obj;

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        return Double.compare(thisBase, otherBase) == 0;
    }
}