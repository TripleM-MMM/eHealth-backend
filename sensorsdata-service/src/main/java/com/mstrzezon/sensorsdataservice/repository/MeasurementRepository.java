package com.mstrzezon.sensorsdataservice.repository;

import com.mstrzezon.sensorsdataservice.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

}
