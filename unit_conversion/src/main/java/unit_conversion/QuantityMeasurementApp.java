package unit_conversion;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        QuantityLength q1 = new QuantityLength(1.0, Unit.FEET);
        System.out.println("1 Feet in Inches = " + q1.convertTo(Unit.INCHES));

        QuantityLength q2 = new QuantityLength(3.0, Unit.FEET);
        System.out.println("3 Feet in Yard = " + q2.convertTo(Unit.YARD));

        QuantityLength q3 = new QuantityLength(30.48, Unit.CENTIMETER);
        System.out.println("30.48 cm in Feet = " + q3.convertTo(Unit.FEET));
    }
}