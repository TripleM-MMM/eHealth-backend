package com.mstrzezon.deviceservice.repository;

import com.mstrzezon.deviceservice.dto.DeviceRequest;
import com.mstrzezon.deviceservice.dto.DeviceResponse;
import com.mstrzezon.deviceservice.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<DeviceResponse> getDevice() {
        return deviceService.getAllDevices();
    }
}
