package com.apps.quantitymeasurement.unit;


public interface IMeasurableUnit {
    public double getConversionFactor();
    public String getUnitName();
    public String getMeasurementType();
}