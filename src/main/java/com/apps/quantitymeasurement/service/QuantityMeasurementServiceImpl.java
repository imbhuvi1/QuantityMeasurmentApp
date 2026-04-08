package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.model.QuantityModel;
import com.apps.quantitymeasurement.repository.QuantityMeasurementRepository;
import com.apps.quantitymeasurement.unit.*;
import com.apps.quantitymeasurement.user.UserEntity;
import com.apps.quantitymeasurement.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService{
    private final QuantityMeasurementRepository repository;
    private final UserRepository userRepository;

    public QuantityMeasurementServiceImpl(QuantityMeasurementRepository repository, UserRepository userRepository){
        this.repository = repository;
        this.userRepository = userRepository;
    }

    private UserEntity getCurrentUser() throws QuantityMeasurementException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            throw new QuantityMeasurementException("User not authenticated");
        }
        String email = auth.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new QuantityMeasurementException("User not found"));
    }

    public boolean compare(QuantityDTO thisQuantityDTO , QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        UserEntity currentUser = getCurrentUser();
        QuantityModel<?> thisQuantityModel = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> thatQuantityModel = getQuantityModel(thatQuantityDTO);
        boolean comparisonResult = compare(thisQuantityModel,thatQuantityModel);
        String resultString = comparisonResult ? "True" : "False";
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityModel,thatQuantityModel,"COMPARE",resultString);
        entity.setUser(currentUser);
        repository.save(entity);
        return comparisonResult;
    }

    private boolean compare(QuantityModel<?> thisQuantity, QuantityModel<?> thatQuantity) throws QuantityMeasurementException{
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

    @Override
    public QuantityDTO convert(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        UserEntity currentUser = getCurrentUser();
        QuantityModel<?> source = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> target = getQuantityModel(thatQuantityDTO);
        if (!source.getUnit().getMeasurementType().equals(target.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot convert between different measurement types");
        }
        if (source.getUnit() instanceof TemperatureUnit) {
            return convertTo(source, target, currentUser);
        }
        double baseValue = source.getValue() * source.getUnit().getConversionFactor();
        double convertedValue = baseValue / target.getUnit().getConversionFactor();
        QuantityModel<?> resultModel = new QuantityModel<>(convertedValue, target.getUnit());
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(source, "CONVERT", resultModel);
        entity.setUser(currentUser);
        repository.save(entity);
        return new QuantityDTO(convertedValue, target.getUnit().getUnitName(), target.getUnit().getMeasurementType());
    }

    public QuantityDTO convertTo(QuantityModel<?> thisQuantityModel , QuantityModel<?> thatQuantityModel, UserEntity currentUser){
        TemperatureUnit thisUnit = (TemperatureUnit) thisQuantityModel.getUnit();
        TemperatureUnit thatUnit = (TemperatureUnit) thatQuantityModel.getUnit();
        double newValue = thisUnit.convertTo(thisQuantityModel.getValue(),thatUnit);
        QuantityModel<TemperatureUnit> resultModel = new QuantityModel<>(newValue,thatUnit);
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(thisQuantityModel,"CONVERT",resultModel);
        entity.setUser(currentUser);
        repository.save(entity);
        return new QuantityDTO(newValue,thatUnit.getUnitName(),thatUnit.getMeasurementType());
    }

    @Override
    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        UserEntity currentUser = getCurrentUser();
        QuantityModel<?> q1 = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> q2 = getQuantityModel(thatQuantityDTO);
        if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot add different measurement types");
        }

        double baseValue1 = q1.getValue() * q1.getUnit().getConversionFactor();
        double baseValue2 = q2.getValue() * q2.getUnit().getConversionFactor();
        double resultBase = baseValue1 + baseValue2;
        double resultValue = resultBase / q1.getUnit().getConversionFactor();
        QuantityModel<?> resultModel = new QuantityModel<>(resultValue, q1.getUnit());
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "ADD", resultModel);
        entity.setUser(currentUser);
        repository.save(entity);
        return new QuantityDTO(resultValue, q1.getUnit().getUnitName(), q1.getUnit().getMeasurementType());
    }

    @Override
    public QuantityDTO add(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) throws QuantityMeasurementException {
        UserEntity currentUser = getCurrentUser();
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
        QuantityModel<?> resultModel = new QuantityModel<>(converted, target.getUnit());
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "ADD", resultModel);
        entity.setUser(currentUser);
        repository.save(entity);
        return new QuantityDTO(converted, target.getUnit().getUnitName(), target.getUnit().getMeasurementType());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        UserEntity currentUser = getCurrentUser();
        QuantityModel<?> q1 = getQuantityModel(thisQuantityDTO);
        QuantityModel<?> q2 = getQuantityModel(thatQuantityDTO);
        if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType())) {
            throw new QuantityMeasurementException("Cannot subtract different measurement types");
        }

        double baseValue1 = q1.getValue() * q1.getUnit().getConversionFactor();
        double baseValue2 = q2.getValue() * q2.getUnit().getConversionFactor();
        double resultBase = baseValue1 - baseValue2;
        double resultValue = resultBase / q1.getUnit().getConversionFactor();
        QuantityModel<?> resultModel = new QuantityModel<>(resultValue, q1.getUnit());
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "SUBTRACT", resultModel);
        entity.setUser(currentUser);
        repository.save(entity);
        return new QuantityDTO(resultValue, q1.getUnit().getUnitName(), q1.getUnit().getMeasurementType());
    }

    @Override
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO, QuantityDTO targetUnitDTO) throws QuantityMeasurementException {
        UserEntity currentUser = getCurrentUser();
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
        QuantityModel<?> resultModel = new QuantityModel<>(converted, target.getUnit());
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "SUBTRACT", resultModel);
        entity.setUser(currentUser);
        repository.save(entity);
        return new QuantityDTO(converted, target.getUnit().getUnitName(), target.getUnit().getMeasurementType());
    }

    @Override
    public double divide(QuantityDTO thisQuantityDTO, QuantityDTO thatQuantityDTO) throws QuantityMeasurementException {
        UserEntity currentUser = getCurrentUser();
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

        double result = base1 / base2;

        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(q1, q2, "DIVIDE", String.valueOf(result));
        entity.setUser(currentUser);
        repository.save(entity);
        return result;
    }

    @Override
    public List<QuantityMeasurementEntity> getHistory() throws QuantityMeasurementException {
        UserEntity currentUser = getCurrentUser();
        return repository.findAllByUserOrderByIdDesc(currentUser);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllHistory() throws QuantityMeasurementException {
        UserEntity currentUser = getCurrentUser();
        repository.deleteAllByUser(currentUser);
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