package addition_target;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, Unit.FEET);
        QuantityLength q2 = new QuantityLength(12.0, Unit.INCHES);

        QuantityLength result = q1.add(q2, Unit.INCHES);

        System.out.println("Result in Inches = " + result.convertTo(Unit.INCHES));
    }
}