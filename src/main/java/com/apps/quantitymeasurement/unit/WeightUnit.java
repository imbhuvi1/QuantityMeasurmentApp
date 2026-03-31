package com.apps.quantitymeasurement.unit;

public enum WeightUnit implements IMeasurableUnit {

    MILLIGRAM(1.0/1000),
    GRAM(1.0),
    KILOGRAM(1000),
    POUND(453.59237),
    TONNE(1e6);

    private double conversionFactor;

    WeightUnit(double conversionFactor){
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
        return "WEIGHT";
    }
}