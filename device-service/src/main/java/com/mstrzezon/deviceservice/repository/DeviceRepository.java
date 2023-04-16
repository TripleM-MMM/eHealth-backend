package com.mstrzezon.deviceservice.repository;

import com.mstrzezon.deviceservice.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeviceRepository extends MongoRepository<Device, String> {
}
