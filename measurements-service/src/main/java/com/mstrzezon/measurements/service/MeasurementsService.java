package com.mstrzezon.measurements.service;

import com.mstrzezon.measurements.dto.MeasurementDTO;
import com.mstrzezon.measurements.model.Measurement;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public abstract class MeasurementsService {

    abstract Measurement getMeasurement(Long deviceId);

    abstract void saveMeasurement(Measurement measurement);

    public List<MeasurementDTO> getMeasurements(Long deviceId) {
        Measurement measurement = getMeasurement(deviceId);
        return measurement.getMeasurements();
    }

    void addMeasurements(Long deviceId, List<MeasurementDTO> measurements) {
        Measurement measurement = getMeasurement(deviceId);
        measurement.getMeasurements().addAll(measurements);
        if (isNull(measurement.getStartDate())) {
            measurement.setStartDate(measurements.get(0).getTimestamp());
        }
        measurement.setEndDate(measurements.get(measurements.size() - 1).getTimestamp());
        saveMeasurement(measurement);
    }
}
