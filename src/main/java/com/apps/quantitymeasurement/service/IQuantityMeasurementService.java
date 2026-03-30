package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.dto.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;

import java.time.LocalDateTime;
import java.util.List;

public interface IQuantityMeasurementService {
    public boolean compare (QuantityDTO thisQuantityDTO , QuantityDTO thatQuantityDTO) throws QuantityMeasurementException;
    public QuantityDTO convert(QuantityDTO thisQuantityDTO , QuantityDTO thatQuantityDTO) throws QuantityMeasurementException;
    public QuantityDTO add(QuantityDTO thisQuantityDTO , QuantityDTO thatQuantityDTO) throws QuantityMeasurementException;
    public QuantityDTO add(QuantityDTO thisQuantityDTO , QuantityDTO thatQuantityDTO , QuantityDTO targetUnitDTO) throws QuantityMeasurementException;
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO , QuantityDTO thatQuantityDTO) throws QuantityMeasurementException;
    public QuantityDTO subtract(QuantityDTO thisQuantityDTO , QuantityDTO thatQuantityDTO , QuantityDTO targetUnitDTO) throws QuantityMeasurementException;
    public double divide(QuantityDTO thisQuantityDTO , QuantityDTO thatQuantityDTO) throws QuantityMeasurementException;
    List<QuantityMeasurementEntity> getHistory();
    List<QuantityMeasurementEntity> findByOperation(String Operation);
    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);
    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);
    List<QuantityMeasurementEntity> findByOperationAndIsErrorFalse(String operation);
    long countByOperationAndIsErrorFalse(String operation);
    List<QuantityMeasurementEntity> findByIsErrorTrue();
    void deleteById(Long id);
}