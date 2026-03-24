package weight_measurement;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Equality: " + w1.equals(w2));

        QuantityWeight result = w1.add(w2, WeightUnit.GRAM);

        System.out.println("Addition Result in Grams: " + result.convertTo(WeightUnit.GRAM));
    }
}