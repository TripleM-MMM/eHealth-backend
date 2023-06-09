package com.mstrzezon.measurements.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MeasurementDTO {
    private LocalDateTime timestamp;
    private Double value;
}

