package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.unit.WeightUnit;

public class QuantityWeightApp {

    public static void main(String[] args) {

        QuantityWeight w1 = new QuantityWeight(1.0, WeightUnit.KILOGRAM);
        QuantityWeight w2 = new QuantityWeight(1000.0, WeightUnit.GRAM);

        System.out.println("Equality: " + w1.equals(w2));

        System.out.println(
                w1.convertTo(WeightUnit.GRAM)
        );

        System.out.println(
                w1.add(w2)
        );

        System.out.println(
                w1.add(w2, WeightUnit.POUND)
        );
    }
}