package com.quantitymeasurement.repository;

import java.util.List;


import com.quantitymeasurement.entity.QuantityMeasurementEntity;

public interface IQuantityMeasurementRepository {
	void save(QuantityMeasurementEntity entity);

	List<QuantityMeasurementEntity> getAllMeasurements();
}