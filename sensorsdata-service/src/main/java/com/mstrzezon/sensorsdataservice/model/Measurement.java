package com.mstrzezon.sensorsdataservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "device")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Measurement {
    @Id
    private Long id;

    private String sensorId;

    private double temperature;

    private double humidity;

    private double pressure;

    private double alcohol_level;
}
