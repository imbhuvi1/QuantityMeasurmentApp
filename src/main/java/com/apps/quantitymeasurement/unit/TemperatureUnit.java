package com.apps.quantitymeasurement.unit;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurableUnit {
    CELSIUS,
    FAHRENHEIT,
    KELVIN;

    final Function<Double,Double> FAHRENHEIT_TO_CELSIUS = (fahrenheit) -> (fahrenheit - 32) * (5.0 / 9.0) ;
    final Function<Double,Double> CELSIUS_TO_FAHRENHEIT = (celsius) -> (celsius * (9.0/5.0)) + 32;
    final Function<Double,Double> CELSIUS_TO_KELVIN = (celsius) -> (celsius + 273.15);
    final Function<Double,Double> KELVIN_TO_CELSIUS = (celsius) -> (celsius - 273.15);
    final Function<Double,Double> FAHRENHEIT_TO_KELVIN = (fahrenheit) -> (CELSIUS_TO_KELVIN.apply(FAHRENHEIT_TO_CELSIUS.apply(fahrenheit)));
    final Function<Double,Double> KELVIN_TO_FAHRENHEIT = (kelvin) -> (CELSIUS_TO_FAHRENHEIT.apply(KELVIN_TO_CELSIUS.apply(kelvin)));

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    public double convertToBaseUnit(double value) {
        return convertTo(value,TemperatureUnit.CELSIUS);
    }

    public double convertTo(double value , TemperatureUnit targetUnit){
        if(this == FAHRENHEIT){
            if(targetUnit == CELSIUS){
                return this.FAHRENHEIT_TO_CELSIUS.apply(value);
            } else if (targetUnit == FAHRENHEIT) {
                return value;
            } else if(targetUnit == KELVIN){
                return this.FAHRENHEIT_TO_KELVIN.apply(value);
            }
        }
        else if(this == CELSIUS){
            if(targetUnit == FAHRENHEIT){
                return this.CELSIUS_TO_FAHRENHEIT.apply(value);
            } else if (targetUnit == CELSIUS) {
                return value;
            } else if(targetUnit == KELVIN){
                return this.CELSIUS_TO_KELVIN.apply(value);
            }
        }
        else if(this == KELVIN){
            if(targetUnit == FAHRENHEIT){
                return this.KELVIN_TO_FAHRENHEIT.apply(value);
            } else if (targetUnit == CELSIUS) {
                return KELVIN_TO_CELSIUS.apply(value);
            } else if(targetUnit == KELVIN){
                return value;
            }
        }
        return 0.0;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public String getMeasurementType() {
        return "TEMPERATURE";
    }

}