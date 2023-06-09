package com.mstrzezon.measurements.service;

import com.mstrzezon.measurements.model.AlcoholLevelMeasurement;
import com.mstrzezon.measurements.model.Measurement;
import com.mstrzezon.measurements.repository.AlcoholLevelMeasurementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AlcoholLevelMeasurementsService extends MeasurementsService {

    private final AlcoholLevelMeasurementRepository repository;

    @Override
    Measurement getMeasurement(Long deviceId) {
        return repository.findByDeviceId(deviceId).stream().findFirst().orElse(AlcoholLevelMeasurement.builder().deviceId(deviceId).measurements(new ArrayList<>()).build());
    }

    @Override
    void saveMeasurement(Measurement measurement) {
        repository.save((AlcoholLevelMeasurement) measurement);
    }
}
