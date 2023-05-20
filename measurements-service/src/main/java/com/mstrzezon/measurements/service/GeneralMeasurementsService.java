package com.mstrzezon.measurements.service;

import com.mstrzezon.measurements.dto.SynchronizeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeneralMeasurementsService {


    private final TemperatureMeasurementsService temperatureMeasurementsService;
    private final PulseMeasurementsService pulseMeasurementsService;
    private final OxygenSaturationMeasurementsService oxygenSaturationMeasurementsService;
    private final AlcoholLevelMeasurementsService alcoholLevelMeasurementsService;

    public void synchronizeData(SynchronizeRequest synchronizeRequest) {
        temperatureMeasurementsService.addMeasurements(synchronizeRequest.getDeviceId(), synchronizeRequest.getTemperatureMeasurements());
        pulseMeasurementsService.addMeasurements(synchronizeRequest.getDeviceId(), synchronizeRequest.getPulseMeasurements());
        oxygenSaturationMeasurementsService.addMeasurements(synchronizeRequest.getDeviceId(), synchronizeRequest.getOxygenSaturationMeasurements());
        alcoholLevelMeasurementsService.addMeasurements(synchronizeRequest.getDeviceId(), synchronizeRequest.getAlcoholLevelMeasurements());
    }
}


