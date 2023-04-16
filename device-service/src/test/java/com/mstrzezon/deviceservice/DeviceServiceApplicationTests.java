package com.mstrzezon.deviceservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import com.mstrzezon.deviceservice.dto.DeviceRequest;
import com.mstrzezon.deviceservice.repository.DeviceRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Disabled("This test is disabled because it requires a docker environment")
class DeviceServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private DeviceRepository deviceRepository;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		DeviceRequest deviceRequest = getDeviceRequest();
		String deviceRequestString = objectMapper.writeValueAsString(deviceRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/device")
						.contentType(MediaType.APPLICATION_JSON)
						.content(deviceRequestString))
				.andExpect(status().isCreated());
		Assertions.assertTrue(deviceRepository.findAll().size() == 1);
	}

	private DeviceRequest getDeviceRequest() {
		return DeviceRequest.builder()
				.name("test")
				.description("test")
				.build();
	}

}
