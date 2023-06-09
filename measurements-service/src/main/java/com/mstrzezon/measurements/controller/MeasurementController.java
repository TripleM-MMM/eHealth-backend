package com.mstrzezon.measurements.controller;

import com.mstrzezon.measurements.dto.MeasurementDTO;
import com.mstrzezon.measurements.dto.SynchronizeRequest;
import com.mstrzezon.measurements.service.GeneralMeasurementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/measurements")
@RequiredArgsConstructor
public class MeasurementController {

    private final GeneralMeasurementsService generalMeasurementsService;

    @PostMapping("/sync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void synchronizeData(@RequestBody SynchronizeRequest synchronizeRequest) {
        generalMeasurementsService.synchronizeData(synchronizeRequest);
    }

    @GetMapping("/device/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<MeasurementDTO> getMeasurements(@PathVariable("id") Long id) {
        return Collections.singletonList(MeasurementDTO.builder().build());
    }

}
