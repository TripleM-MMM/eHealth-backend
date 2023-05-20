package com.mstrzezon.measurements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mstrzezon.measurements.repository.TemperatureMeasurementRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Disabled("This test is disabled because it requires a docker environment")
class MeasurementsServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TemperatureMeasurementRepository temperatureMeasurementRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
//        MeasurementRequest deviceInDTO = getDeviceRequest();
//        String deviceRequestString = objectMapper.writeValueAsString(deviceInDTO);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/device")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(deviceRequestString))
//                .andExpect(status().isCreated());
//        Assertions.assertTrue(deviceRepository.findAll().size() == 1);
    }

//    private DeviceInDTO getDeviceRequest() {
//        return DeviceInDTO.builder()
//                .name("test")
//                .description("test")
//                .build();
//    }

}
