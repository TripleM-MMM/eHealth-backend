package com.mstrzezon.deviceservice.service;

import com.mstrzezon.deviceservice.dto.DeviceInDTO;
import com.mstrzezon.deviceservice.dto.DeviceOutDTO;
import com.mstrzezon.deviceservice.model.Device;

public class DeviceUtils {

    protected static final String DEVICE_SERVICE_URL = "http://device-service";

    static Device mapToDevice(DeviceInDTO deviceInDTO) {
        return Device.builder()
                .name(deviceInDTO.getName())
                .description(deviceInDTO.getDescription())
                .build();
    }

    static DeviceOutDTO mapToDeviceOutDTO(Device device) {
        return DeviceOutDTO.builder()
                .id(device.getId())
                .name(device.getName())
                .description(device.getDescription())
                .build();
    }

    static Device resetDevice(Device device) {
        return Device.builder()
                .name(device.getName())
                .description(device.getDescription())
                .build();
    }
}
