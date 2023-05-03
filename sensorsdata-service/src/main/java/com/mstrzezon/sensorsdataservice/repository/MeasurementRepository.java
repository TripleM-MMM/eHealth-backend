package com.mstrzezon.sensorsdataservice.repository;

import com.mstrzezon.sensorsdataservice.model.Measurement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends MongoRepository<Measurement, Long> {

}
