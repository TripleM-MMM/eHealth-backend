package com.mstrzezon.measurements.service;

import com.mstrzezon.measurements.model.Measurement;
import com.mstrzezon.measurements.model.PulseMeasurement;
import com.mstrzezon.measurements.repository.PulseMeasurementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class PulseMeasurementsService extends MeasurementsService {

    private final PulseMeasurementRepository repository;

    @Override
    Measurement getMeasurement(Long deviceId) {
        return repository.findByDeviceId(deviceId).stream().findFirst().orElse(PulseMeasurement.builder().deviceId(deviceId).measurements(new ArrayList<>()).build());
    }

    @Override
    void saveMeasurement(Measurement measurement) {
        repository.save((PulseMeasurement) measurement);
    }
}
