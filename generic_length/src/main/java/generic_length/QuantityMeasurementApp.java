package generic_length;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, Unit.INCHES);

        System.out.println("Equal: " + q1.equals(q2));
    }
}