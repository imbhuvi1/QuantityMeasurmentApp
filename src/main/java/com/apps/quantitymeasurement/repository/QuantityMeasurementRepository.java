package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    List<QuantityMeasurementEntity> findByOperation(String Operation);
    List<QuantityMeasurementEntity> findByThisMeasurementType(String measurementType);
    List<QuantityMeasurementEntity> findByCreatedAtAfter(LocalDateTime date);
    List<QuantityMeasurementEntity> findByOperationAndIsErrorFalse(String operation);
    long countByOperationAndIsErrorFalse(String operation);
    List<QuantityMeasurementEntity> findByIsErrorTrue();
    void deleteById(Long id);
}