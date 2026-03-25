package com.quantitymeasurement;

public class Feet {

    private final double value;

    public Feet(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {

        // same object
        if (this == obj) return true;

        // null or different class
        if (obj == null || getClass() != obj.getClass()) return false;

        // cast
        Feet other = (Feet) obj;

        // compare safely
        return Double.compare(this.value, other.value) == 0;
    }
}
