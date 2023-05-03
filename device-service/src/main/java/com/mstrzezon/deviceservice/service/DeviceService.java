package com.mstrzezon.deviceservice.service;

import com.mstrzezon.deviceservice.dto.DeviceInDTO;
import com.mstrzezon.deviceservice.dto.DeviceOutDTO;
import com.mstrzezon.deviceservice.model.Device;
import com.mstrzezon.deviceservice.model.User;
import com.mstrzezon.deviceservice.repository.DeviceRepository;
import com.mstrzezon.deviceservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.mstrzezon.deviceservice.service.DeviceUtils.mapToDevice;
import static com.mstrzezon.deviceservice.service.DeviceUtils.mapToDeviceOutDTO;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;

    private final UserRepository userRepository;

    private final WebClient.Builder webClientBuilder;

    public String getMeasurement() {
        return webClientBuilder.build().get()
                .uri("http://sensorsdata-service/api/measurement")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<DeviceOutDTO> getDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream().map(DeviceUtils::mapToDeviceOutDTO).toList();
    }

    public DeviceOutDTO createDevice(DeviceInDTO deviceInDTO) {
        Device device = mapToDevice(deviceInDTO);
        Device savedDevice = deviceRepository.save(device);
        return mapToDeviceOutDTO(savedDevice);
    }

    public DeviceOutDTO updateDevice(Long id, DeviceInDTO deviceInDTO) {
        Device device = mapToDevice(deviceInDTO);
        device.setId(id);
        Device savedDevice = deviceRepository.save(device);
        return mapToDeviceOutDTO(savedDevice);
    }

    public DeviceOutDTO getDevice(Long id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new RuntimeException("Device not found"));
        return mapToDeviceOutDTO(device);
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    public void shareDevice(Long deviceId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("Device not found"));
        device.getSharedWith().add(user);
        deviceRepository.save(device);
    }

    public void unshareDevice(Long deviceId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("Device not found"));
        device.getSharedWith().remove(user);
        deviceRepository.save(device);
    }

    public List<Long> getSharedUsers(Long deviceId) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("Device not found"));
        return device.getSharedWith().stream().map(User::getId).toList();
    }

    public DeviceOutDTO resetDevice(Long deviceId) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("Device not found"));
        device = DeviceUtils.resetDevice(device);
        deviceRepository.save(device);
        return mapToDeviceOutDTO(device);
    }

    public DeviceOutDTO changeOwner(Long deviceId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("Device not found"));
        device.setOwner(user);
        deviceRepository.save(device);
        return mapToDeviceOutDTO(device);
    }

    public void deleteOwner(Long deviceId) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new RuntimeException("Device not found"));
        device.setOwner(null);
        deviceRepository.save(device);
    }
}
