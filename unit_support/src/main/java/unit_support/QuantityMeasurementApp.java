package unit_support;


public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, Unit.YARD);
        QuantityLength q2 = new QuantityLength(3.0, Unit.FEET);

        System.out.println("Equal: " + q1.equals(q2));
    }
}