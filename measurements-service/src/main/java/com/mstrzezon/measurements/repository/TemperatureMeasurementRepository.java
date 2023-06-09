package com.mstrzezon.measurements.repository;

import com.mstrzezon.measurements.model.TemperatureMeasurement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureMeasurementRepository extends MongoRepository<TemperatureMeasurement, String> {
    List<TemperatureMeasurement> findByDeviceId(Long name);

}
