package com.apps.quantitymeasurement.model;

import com.apps.quantitymeasurement.unit.IMeasurableUnit;
import lombok.Getter;

public class QuantityModel<U extends IMeasurableUnit> {
    @Getter public double value;
    @Getter public U unit;

    public QuantityModel(double value , U unit) {
        this.value = value;
        this.unit = unit;
    }

    @Override
    public String toString(){
        return this.value+" "+this.unit;
    }
}