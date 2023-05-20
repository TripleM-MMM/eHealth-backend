package com.mstrzezon.measurements.model;

import com.mstrzezon.measurements.dto.MeasurementDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Measurement {
    @Id
    private String id;
    private Long deviceId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<MeasurementDTO> measurements;
}
