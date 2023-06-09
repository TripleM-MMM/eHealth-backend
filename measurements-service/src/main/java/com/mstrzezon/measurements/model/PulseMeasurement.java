package com.mstrzezon.measurements.model;


import com.mstrzezon.measurements.dto.MeasurementDTO;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(value = "pulse_measurements")
public class PulseMeasurement extends Measurement {
    @Builder
    public PulseMeasurement(String id, Long deviceId, LocalDateTime startDate, LocalDateTime endDate, List<MeasurementDTO> measurements) {
        super(id, deviceId, startDate, endDate, measurements);
    }
}
