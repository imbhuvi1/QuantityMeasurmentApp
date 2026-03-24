package refactor_length;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Quantity q1 = new Quantity(1.0, Unit.FEET);
        Quantity q2 = new Quantity(12.0, Unit.INCHES);

        System.out.println("Equality: " + q1.equals(q2));

        Quantity result = q1.add(q2, Unit.INCHES);

        System.out.println("Addition Result in Inches: " + result.convertTo(Unit.INCHES));
    }
}