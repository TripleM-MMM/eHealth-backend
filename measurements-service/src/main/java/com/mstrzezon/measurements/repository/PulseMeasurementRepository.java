package com.mstrzezon.measurements.repository;

import com.mstrzezon.measurements.model.PulseMeasurement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PulseMeasurementRepository extends MongoRepository<PulseMeasurement, String> {
    List<PulseMeasurement> findByDeviceId(Long name);
}
