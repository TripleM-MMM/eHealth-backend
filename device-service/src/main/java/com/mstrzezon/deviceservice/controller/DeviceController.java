package com.mstrzezon.deviceservice.controller;

import com.mstrzezon.deviceservice.dto.DeviceRequest;
import com.mstrzezon.deviceservice.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createDevice(@RequestBody DeviceRequest deviceRequest) {
        deviceService.createDevice(deviceRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getDevice() {
        return "device";
    }
}
