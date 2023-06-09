package com.mstrzezon.measurements.repository;

import com.mstrzezon.measurements.model.OxygenSaturationMeasurement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OxygenSaturationMeasurementRepository extends MongoRepository<OxygenSaturationMeasurement, String> {
    List<OxygenSaturationMeasurement> findByDeviceId(Long name);
}
