package com.quantitymeasurement;

public class QuantityMeasurementApp {

    public static void main(String[] args) {

        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        Inches i1 = new Inches(12.0);
        Inches i2 = new Inches(12.0);

        System.out.println("Feet Equal: " + f1.equals(f2));
        System.out.println("Inches Equal: " + i1.equals(i2));
    }
}