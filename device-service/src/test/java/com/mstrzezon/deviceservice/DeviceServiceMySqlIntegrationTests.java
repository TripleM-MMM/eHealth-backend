package com.mstrzezon.deviceservice;

import com.mstrzezon.deviceservice.model.Device;
import com.mstrzezon.deviceservice.model.User;
import com.mstrzezon.deviceservice.repository.DeviceRepository;
import com.mstrzezon.deviceservice.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
class DeviceServiceMySqlIntegrationTests {

    @Container
    static MySQLContainer<?> container = new MySQLContainer<>("mysql:8.0.33");

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private UserRepository userRepository;

    @DynamicPropertySource
    static void mysqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @Test
    @Transactional
    public void shouldReturnTwoDevices() {
        insertUsers();
        assertEquals(2, deviceRepository.findAll().size());
    }

    @Test
    @Transactional
    public void shouldAddUserToShared() {
        insertUsers();
        User user = User.builder()
                .id("user3")
                .build();
        userRepository.save(user);
        userRepository.flush();
        Optional<Device> optionalDevice = deviceRepository.findById(1L);
        assertTrue(optionalDevice.isPresent());
        Device device = optionalDevice.get();
        device.getSharedWith().add(user);
        deviceRepository.save(device);
        assertEquals(2, deviceRepository.findAll().size());
        Optional<Device> optionalUpdatedDevice = deviceRepository.findById(1L);
        assertTrue(optionalUpdatedDevice.isPresent());
        Device updatedDevice = optionalUpdatedDevice.get();
        assertEquals(device, updatedDevice);
    }

    public void insertUsers() {
        User user1 = User.builder().id("user1").build();
        User user2 = User.builder().id("user2").build();
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.flush();
        Device device1 = Device.builder()
                .id(1L)
                .name("Device 1")
                .description("Description 1")
                .owner(user1)
                .sharedWith(new HashSet<>(Collections.singletonList(user2)))
                .build();
        Device device2 = Device.builder()
                .id(2L)
                .name("Device 2")
                .description("Description 2")
                .owner(user2)
                .sharedWith(new HashSet<>(Collections.singletonList(user1)))
                .build();
        deviceRepository.save(device1);
        deviceRepository.save(device2);
        deviceRepository.flush();
    }
}
