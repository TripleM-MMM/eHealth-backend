package com.mstrzezon.deviceservice.service;

import com.mstrzezon.deviceservice.dto.DeviceRequest;
import com.mstrzezon.deviceservice.dto.DeviceResponse;
import com.mstrzezon.deviceservice.model.Device;
import com.mstrzezon.deviceservice.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public void createDevice(DeviceRequest deviceRequest) {
        Device device = Device.builder()
                .name(deviceRequest.getName())
                .description(deviceRequest.getDescription())
                .build();
        deviceRepository.save(device);
        log.info("Device created: {}", device);
    }

    public List<DeviceResponse> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();

        return devices.stream().map(this::mapToDeviceResponse).toList();
    }

    private DeviceResponse mapToDeviceResponse(Device device) {
        return DeviceResponse.builder()
                .id(device.getId())
                .name(device.getName())
                .description(device.getDescription())
                .build();
    }
}
