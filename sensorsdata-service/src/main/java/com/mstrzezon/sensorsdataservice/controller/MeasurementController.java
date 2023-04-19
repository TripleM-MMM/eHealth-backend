package com.mstrzezon.sensorsdataservice.controller;

import com.mstrzezon.sensorsdataservice.dto.MeasurementRequest;
import com.mstrzezon.sensorsdataservice.service.MeasurementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/measurement")
@RequiredArgsConstructor
public class MeasurementController {

    private final MeasurementService measurementService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String addMeasurement(@RequestBody MeasurementRequest measurementRequest) {
        measurementService.addMeasurement(measurementRequest);
        return "Measurement added successfully";
    }
}
