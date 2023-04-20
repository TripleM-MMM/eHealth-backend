package com.mstrzezon.deviceservice.service;

import com.mstrzezon.deviceservice.dto.DeviceRequest;
import com.mstrzezon.deviceservice.dto.DeviceResponse;
import com.mstrzezon.deviceservice.model.Device;
import com.mstrzezon.deviceservice.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;

    private final WebClient.Builder webClientBuilder;

    public void createDevice(DeviceRequest deviceRequest) {
        Device device = Device.builder()
                .name(deviceRequest.getName())
                .description(deviceRequest.getDescription())
                .build();
//        deviceRepository.save(device);
        log.info("Device created: {}", device);
    }

    public List<DeviceResponse> getAllDevices() {
//        List<Device> devices = deviceRepository.findAll();

//        return devices.stream().map(this::mapToDeviceResponse).toList();

        return Collections.emptyList();
    }

    public String getMeasurement() {
        return webClientBuilder.build().get()
                .uri("http://sensorsdata-service/api/measurement")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    private DeviceResponse mapToDeviceResponse(Device device) {
        return DeviceResponse.builder()
                .id(device.getId())
                .name(device.getName())
                .description(device.getDescription())
                .build();
    }
}
