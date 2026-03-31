package com.apps.quantitymeasurement.unit;

public enum LengthUnit implements IMeasurableUnit {
    FEET(1.0),
    INCHES(1.0/12),
    YARDS(3.0),
    CENTIMETERS(0.0328084);

    private double conversionFactor;

    LengthUnit(double conversionFactor){
        this.conversionFactor = conversionFactor;
    }

    @Override
    public double getConversionFactor() {
        return this.conversionFactor;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public String getMeasurementType() {
        return "LENGTH";
    }
}