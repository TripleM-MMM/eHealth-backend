package com.mstrzezon.deviceservice.controller;

import com.mstrzezon.deviceservice.dto.DeviceInDTO;
import com.mstrzezon.deviceservice.dto.DeviceOutDTO;
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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DeviceOutDTO> getDevices() {
        return deviceService.getDevices();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceOutDTO createDevice(@RequestBody DeviceInDTO deviceInDTO) {
        return deviceService.createDevice(deviceInDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceOutDTO updateDevice(@PathVariable Long id, @RequestBody DeviceInDTO deviceInDTO) {
        return deviceService.updateDevice(id, deviceInDTO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceOutDTO getDevice(@PathVariable("id") Long id) {
        return deviceService.getDevice(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDevice(@PathVariable("id") Long id) {
        deviceService.deleteDevice(id);
    }

    @PostMapping("/{device_id}/owner/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceOutDTO changeOwner(@PathVariable("device_id") Long deviceId, @PathVariable("user_id") String userId) {
        return deviceService.changeOwner(deviceId, userId);
    }

    @DeleteMapping("/{device_id}/owner")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOwner(@PathVariable("device_id") Long deviceId) {
        deviceService.deleteOwner(deviceId);
    }

    @PostMapping("/{device_id}/share/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public void shareDevice(@PathVariable("device_id") Long deviceId, @PathVariable("user_id") String userId) {
        deviceService.shareDevice(deviceId, userId);
    }

    @PutMapping("/{device_id}/unshare/{user_id}")
    @ResponseStatus(HttpStatus.OK)
    public void unshareDevice(@PathVariable("device_id") Long deviceId, @PathVariable("user_id") String userId) {
        deviceService.unshareDevice(deviceId, userId);
    }

    @GetMapping("/{device_id}/shared-users")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSharedUsers(@PathVariable("device_id") Long deviceId) {
        return deviceService.getSharedUsers(deviceId);
    }

    @PutMapping("/{device_id}/reset")
    @ResponseStatus(HttpStatus.OK)
    public DeviceOutDTO resetDevice(@PathVariable("device_id") Long deviceId) {
        return deviceService.resetDevice(deviceId);
    }
}
