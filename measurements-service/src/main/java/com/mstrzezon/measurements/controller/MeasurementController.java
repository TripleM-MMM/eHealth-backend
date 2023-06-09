package com.mstrzezon.measurements.controller;

import com.mstrzezon.measurements.dto.MeasurementDTO;
import com.mstrzezon.measurements.dto.SynchronizeRequest;
import com.mstrzezon.measurements.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/measurements")
@RequiredArgsConstructor
public class MeasurementController {

    private final GeneralMeasurementsService generalMeasurementsService;

    private final TemperatureMeasurementsService temperatureMeasurementsService;

    private final AlcoholLevelMeasurementsService alcoholLevelMeasurementsService;

    private final OxygenSaturationMeasurementsService oxygenSaturationMeasurementsService;

    private final PulseMeasurementsService pulseMeasurementsService;


    @PostMapping("/sync")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void synchronizeData(@RequestBody SynchronizeRequest synchronizeRequest) {
        generalMeasurementsService.synchronizeData(synchronizeRequest);
    }

    @GetMapping("/device/{id}/temperature")
    @ResponseStatus(HttpStatus.OK)
    public List<MeasurementDTO> getTemperatureMeasurements(@PathVariable("id") Long id) {
        return temperatureMeasurementsService.getMeasurements(id);
    }

    @GetMapping("/device/{id}/alcohol")
    @ResponseStatus(HttpStatus.OK)
    public List<MeasurementDTO> getAlcoholLevelMeasurements(@PathVariable("id") Long id) {
        return alcoholLevelMeasurementsService.getMeasurements(id);
    }

    @GetMapping("/device/{id}/oxygen")
    @ResponseStatus(HttpStatus.OK)
    public List<MeasurementDTO> getOxygenSaturationMeasurements(@PathVariable("id") Long id) {
        return oxygenSaturationMeasurementsService.getMeasurements(id);
    }

    @GetMapping("/device/{id}/pulse")
    @ResponseStatus(HttpStatus.OK)
    public List<MeasurementDTO> getPulseMeasurements(@PathVariable("id") Long id) {
        return pulseMeasurementsService.getMeasurements(id);
    }
}
