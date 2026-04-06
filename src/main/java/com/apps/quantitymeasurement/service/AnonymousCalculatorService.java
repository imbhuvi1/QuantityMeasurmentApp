package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.model.QuantityModel;
import com.apps.quantitymeasurement.unit.*;
import org.springframework.stereotype.Service;

@Service
public class AnonymousCalculatorService {

    public boolean compare(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        QuantityModel<?> thisQuantityModel = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> thatQuantityModel = getQuantityModel(thatQuantityDTO);
        return compare(thisQuantityModel, thatQuantityModel);
    }

    private boolean compare(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity) throws QuantityMeasurementException {
        if (!thisQuantity.getUnit().getMeasurementType().equals(thatQuantity.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot compare different measurement types");
        }
        double baseValue1, baseValue2;
        if (thisQuantity.getUnit() instanceof TemperatureUnit) {
            baseValue1 = ((TemperatureUnit) thisQuantity.getUnit()).convertToBaseUnit(thisQuantity.getValue());
            baseValue2 = ((TemperatureUnit) thatQuantity.getUnit()).convertToBaseUnit(thatQuantity.getValue());
        } else {
            baseValue1 = thisQuantity.getValue() * thisQuantity.getUnit().getConversionFactor();
            baseValue2 = thatQuantity.getValue() * thatQuantity.getUnit().getConversionFactor();
        }
        return Math.abs(baseValue1 - baseValue2) < 0.0001;
    }

    public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        QuantityModel<?> source = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> target = getQuantityModel(thatQuantityDTO);
        if (!source.getUnit().getMeasurementType().equals(target.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot convert between different measurement types");
        }
        if (source.getUnit() instanceof TemperatureUnit) {
            return convertTo(source, target);
        }
        double baseValue = source.getValue() * source.getUnit().getConversionFactor();
        double convertedValue = baseValue / target.getUnit().getConversionFactor();
        return new QuantityDTO(convertedValue, target.getUnit().getUnitName(), target.getUnit().getMeasurementType());
    }

    public QuantityDTO convertTo(QuantityModel<?> thisQuantityModel, QuantityModel<?> thatQuantityModel) {
        TemperatureUnit thisUnit = (TemperatureUnit) thisQuantityModel.getUnit();
        TemperatureUnit thatUnit = (TemperatureUnit) thatQuantityModel.getUnit();
        double newValue = thisUnit.convertTo(thisQuantityModel.getValue(), thatUnit);
        return new QuantityDTO(newValue, thatUnit.getUnitName(), thatUnit.getMeasurementType());
    }

    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        QuantityModel<?> q1 = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> q2 = getQuantityModel(thatQuantityDTO);
        if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot add different measurement types");
        }

        double baseValue1 = q1.getValue() * q1.getUnit().getConversionFactor();
        double baseValue2 = q2.getValue() * q2.getUnit().getConversionFactor();
        double resultBase = baseValue1 + baseValue2;
        double resultValue = resultBase / q1.getUnit().getConversionFactor();
        return new QuantityDTO(resultValue, q1.getUnit().getUnitName(), q1.getUnit().getMeasurementType());
    }

    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) throws QuantityMeasurementException {
        QuantityModel<?> q1 = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> q2 = getQuantityModel(thatQuantityDTO);
        QuantityModel<?> target = getQuantityModel(targetUnitDTO);

        if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot add different measurement types");
        }

        double base1 = q1.getValue() * q1.getUnit().getConversionFactor();
        double base2 = q2.getValue() * q2.getUnit().getConversionFactor();
        double resultBase = base1 + base2;
        double converted = resultBase / target.getUnit().getConversionFactor();
        return new QuantityDTO(converted, target.getUnit().getUnitName(), target.getUnit().getMeasurementType());
    }

    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        QuantityModel<?> q1 = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> q2 = getQuantityModel(thatQuantityDTO);
        if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot subtract different measurement types");
        }

        double baseValue1 = q1.getValue() * q1.getUnit().getConversionFactor();
        double baseValue2 = q2.getValue() * q2.getUnit().getConversionFactor();
        double resultBase = baseValue1 - baseValue2;
        double resultValue = resultBase / q1.getUnit().getConversionFactor();
        return new QuantityDTO(resultValue, q1.getUnit().getUnitName(), q1.getUnit().getMeasurementType());
    }

    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) throws QuantityMeasurementException {
        QuantityModel<?> q1 = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> q2 = getQuantityModel(thatQuantityDTO);
        QuantityModel<?> target = getQuantityModel(targetUnitDTO);

        if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot subtract different measurement types");
        }

        double base1 = q1.getValue() * q1.getUnit().getConversionFactor();
        double base2 = q2.getValue() * q2.getUnit().getConversionFactor();
        double resultBase = base1 - base2;
        double converted = resultBase / target.getUnit().getConversionFactor();
        return new QuantityDTO(converted, target.getUnit().getUnitName(), target.getUnit().getMeasurementType());
    }

    public double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        QuantityModel<?> q1 = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> q2 = getQuantityModel(thatQuantityDTO);
        if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot divide different measurement types");
        }

        double base1 = q1.getValue() * q1.getUnit().getConversionFactor();
        double base2 = q2.getValue() * q2.getUnit().getConversionFactor();
        if (base2 == 0) {
            throw new QuantityMeasurementException("Division by zero");
        }

        return base1 / base2;
    }

    private QuantityModel<?> getQuantityModel(QuantityDTO dto) throws QuantityMeasurementException {
        String unitName = dto.getUnit();
        String type = dto.getMeasurementType();
        IMeasurableUnit unit = null;
        switch (type) {
            case "LENGTH":
                unit = LengthUnit.valueOf(unitName);
                break;
            case "WEIGHT":
                unit = WeightUnit.valueOf(unitName);
                break;
            case "VOLUME":
                unit = VolumeUnit.valueOf(unitName);
                break;
            case "TEMPERATURE":
                unit = TemperatureUnit.valueOf(unitName);
                break;
            default:
                throw new QuantityMeasurementException("Invalid measurement type");
        }
        return new QuantityModel<>(dto.getValue(), unit);
    }
}