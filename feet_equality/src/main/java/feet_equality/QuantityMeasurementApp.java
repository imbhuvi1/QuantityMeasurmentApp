package feet_equality;


public class QuantityMeasurementApp {

    // Inner Class
    public static class Feet {

        // Encapsulation + Immutability
        private final double value;

        // Constructor
        public Feet(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }

        // Overriding equals() method
        @Override
        public boolean equals(Object obj) {

            // Same reference check (Reflexive)
            if (this == obj) {
                return true;
            }

            // Null check + Type check
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            // Safe casting
            Feet feet = (Feet) obj;

            // Compare using Double.compare()
            return Double.compare(this.value, feet.value) == 0;
        }
    }

    // Main method to test
    public static void main(String[] args) {

        Feet value1 = new Feet(1.0);
        Feet value2 = new Feet(1.0);

        boolean result = value1.equals(value2);

        System.out.println("Input: 1.0 ft and 1.0 ft");
        System.out.println("Output: Equal (" + result + ")");
    }
}