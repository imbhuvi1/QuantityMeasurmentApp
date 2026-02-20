package generic_length;


public class QuantityMeasurementApp {

    /* ===========================
       STEP 1: ENUM FOR UNITS
       Base Unit: FEET
    =========================== */
    public enum LengthUnit {

        FEET(1.0),
        INCH(1.0 / 12.0);

        private final double conversionFactorToFeet;

        LengthUnit(double conversionFactorToFeet) {
            this.conversionFactorToFeet = conversionFactorToFeet;
        }

        public double toFeet(double value) {
            return value * conversionFactorToFeet;
        }
    }

    /* ===========================
       STEP 2: GENERIC QUANTITY CLASS
    =========================== */
    public static class QuantityLength {

        private final double value;
        private final LengthUnit unit;

        public QuantityLength(double value, LengthUnit unit) {

            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            this.value = value;
            this.unit = unit;
        }

        private double convertToBaseUnit() {
            return unit.toFeet(value);
        }

        @Override
        public boolean equals(Object obj) {

            if (this == obj) return true;

            if (obj == null || getClass() != obj.getClass())
                return false;

            QuantityLength other = (QuantityLength) obj;

            return Double.compare(
                    this.convertToBaseUnit(),
                    other.convertToBaseUnit()
            ) == 0;
        }
    }

    /* ===========================
       MAIN METHOD
    =========================== */
    public static void main(String[] args) {

        QuantityLength q1 =
                new QuantityLength(1.0, LengthUnit.FEET);

        QuantityLength q2 =
                new QuantityLength(12.0, LengthUnit.INCH);

        System.out.println("Input: Quantity(1.0, FEET) and Quantity(12.0, INCH)");
        System.out.println("Output: Equal (" + q1.equals(q2) + ")");

        QuantityLength q3 =
                new QuantityLength(1.0, LengthUnit.INCH);

        QuantityLength q4 =
                new QuantityLength(1.0, LengthUnit.INCH);

        System.out.println("Input: Quantity(1.0, INCH) and Quantity(1.0, INCH)");
        System.out.println("Output: Equal (" + q3.equals(q4) + ")");
    }
}