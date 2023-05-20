package com.mstrzezon.measurements.controller;

import com.mstrzezon.measurements.dto.SynchronizeRequest;
import com.mstrzezon.measurements.service.GeneralMeasurementsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MeasurementController {

    private final GeneralMeasurementsService generalMeasurementsService;

    @PostMapping("/sync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void synchronizeData(@RequestBody SynchronizeRequest synchronizeRequest) {
        generalMeasurementsService.synchronizeData(synchronizeRequest);
    }

}
