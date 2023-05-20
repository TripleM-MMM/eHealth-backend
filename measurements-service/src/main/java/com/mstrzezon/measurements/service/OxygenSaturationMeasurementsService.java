package com.mstrzezon.measurements.service;

import com.mstrzezon.measurements.model.Measurement;
import com.mstrzezon.measurements.model.OxygenSaturationMeasurement;
import com.mstrzezon.measurements.repository.OxygenSaturationMeasurementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class OxygenSaturationMeasurementsService extends MeasurementsService {

    private final OxygenSaturationMeasurementRepository repository;

    @Override
    Measurement getMeasurement(Long deviceId) {
        return repository.findByDeviceId(deviceId).stream().findFirst().orElse(OxygenSaturationMeasurement.builder().deviceId(deviceId).measurements(new ArrayList<>()).build());
    }

    @Override
    void saveMeasurement(Measurement measurement) {
        repository.save((OxygenSaturationMeasurement) measurement);
    }
}
