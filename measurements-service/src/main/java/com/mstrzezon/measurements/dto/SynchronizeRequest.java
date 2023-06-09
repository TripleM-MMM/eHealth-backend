package com.mstrzezon.measurements.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SynchronizeRequest {
    private Long deviceId;
    private List<MeasurementDTO> temperatureMeasurements;
    private List<MeasurementDTO> pulseMeasurements;
    private List<MeasurementDTO> oxygenSaturationMeasurements;
    private List<MeasurementDTO> alcoholLevelMeasurements;
}
