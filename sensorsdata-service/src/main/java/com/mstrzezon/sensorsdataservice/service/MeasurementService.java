package com.mstrzezon.sensorsdataservice.service;

import com.mstrzezon.sensorsdataservice.dto.MeasurementRequest;
import com.mstrzezon.sensorsdataservice.model.Measurement;
import com.mstrzezon.sensorsdataservice.repository.MeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    public void addMeasurement(MeasurementRequest measurementRequest) {
        Measurement measurement = new Measurement();
        measurement.setSensorId(measurementRequest.getSensorId());
        measurement.setTemperature(measurementRequest.getTemperature());
        measurement.setHumidity(measurementRequest.getHumidity());
        measurement.setPressure(measurementRequest.getPressure());
        measurement.setAlcohol_level(measurementRequest.getAlcohol_level());

        measurementRepository.save(measurement);
    }
}
