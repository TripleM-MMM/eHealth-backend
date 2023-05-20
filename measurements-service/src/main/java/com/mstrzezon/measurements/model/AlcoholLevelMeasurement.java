package com.mstrzezon.measurements.model;


import com.mstrzezon.measurements.dto.MeasurementDTO;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(value = "alcohol_level_measurements")
public class AlcoholLevelMeasurement extends Measurement {
    @Builder
    public AlcoholLevelMeasurement(String id, Long deviceId, LocalDateTime startDate, LocalDateTime endDate, List<MeasurementDTO> measurements) {
        super(id, deviceId, startDate, endDate, measurements);
    }
}
