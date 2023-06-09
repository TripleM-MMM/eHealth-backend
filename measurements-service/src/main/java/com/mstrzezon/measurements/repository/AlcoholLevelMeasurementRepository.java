package com.mstrzezon.measurements.repository;

import com.mstrzezon.measurements.model.AlcoholLevelMeasurement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlcoholLevelMeasurementRepository extends MongoRepository<AlcoholLevelMeasurement, String> {
    List<AlcoholLevelMeasurement> findByDeviceId(Long deviceId);
}
