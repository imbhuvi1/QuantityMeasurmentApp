package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {
    List<QuantityMeasurementEntity> findAllByUserOrderByIdDesc(UserEntity user);
    void deleteAllByUser(UserEntity user);
}