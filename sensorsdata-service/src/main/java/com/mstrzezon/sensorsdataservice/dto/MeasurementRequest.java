package com.mstrzezon.sensorsdataservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementRequest {
    private String sensorId;
    private double temperature;
    private double humidity;
    private double pressure;
    private double alcohol_level;
}
