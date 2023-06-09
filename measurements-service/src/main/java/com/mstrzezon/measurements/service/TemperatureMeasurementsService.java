package com.mstrzezon.measurements.service;

import com.mstrzezon.measurements.model.Measurement;
import com.mstrzezon.measurements.model.TemperatureMeasurement;
import com.mstrzezon.measurements.repository.TemperatureMeasurementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TemperatureMeasurementsService extends MeasurementsService {

    private final TemperatureMeasurementRepository repository;

    @Override
    TemperatureMeasurement getMeasurement(Long deviceId) {
        return repository.findByDeviceId(deviceId).stream().findFirst().orElse(TemperatureMeasurement.builder().deviceId(deviceId).measurements(new ArrayList<>()).build());
    }

    @Override
    void saveMeasurement(Measurement measurement) {
        repository.save((TemperatureMeasurement) measurement);
    }


}
