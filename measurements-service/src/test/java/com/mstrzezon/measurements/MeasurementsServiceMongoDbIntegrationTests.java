package com.mstrzezon.measurements;

import com.mstrzezon.measurements.dto.MeasurementDTO;
import com.mstrzezon.measurements.model.TemperatureMeasurement;
import com.mstrzezon.measurements.repository.TemperatureMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Testcontainers
public class MeasurementsServiceMongoDbIntegrationTests {


    private static final LocalDateTime date = LocalDateTime.of(2020, 12, 12, 12, 0);

    private static final TemperatureMeasurement temperatureMeasurementForDevice1 = TemperatureMeasurement.builder()
            .deviceId(1L)
            .startDate(date)
            .endDate(date.plusDays(1))
            .measurements(Arrays.asList(
                    MeasurementDTO.builder()
                            .timestamp(date)
                            .value(36.6)
                            .build(),
                    MeasurementDTO.builder()
                            .timestamp(date.plusDays(1))
                            .value(36.7)
                            .build()))
            .build();

    private static final TemperatureMeasurement temperatureMeasurementForDevice2 = TemperatureMeasurement.builder()
            .deviceId(2L)
            .startDate(date.plusDays(2))
            .endDate(date.plusDays(3))
            .measurements(Arrays.asList(
                    MeasurementDTO.builder()
                            .timestamp(date.plusDays(2))
                            .value(36.8)
                            .build(),
                    MeasurementDTO.builder()
                            .timestamp(date.plusDays(3))
                            .value(40.0)
                            .build()))
            .build();

    @Container
    static MongoDBContainer container = new MongoDBContainer("mongo:4.4.2");
    @Autowired
    private TemperatureMeasurementRepository temperatureMeasurementRepository;

    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", container::getReplicaSetUrl);
    }

    @BeforeEach
    public void setUp() {
        temperatureMeasurementRepository.save(temperatureMeasurementForDevice1);
        temperatureMeasurementRepository.save(temperatureMeasurementForDevice2);
    }

    @Test
    public void shouldAddTemperatureMeasurement() {
        LocalDateTime date = LocalDateTime.of(2020, 12, 12, 12, 0);
        TemperatureMeasurement temperatureMeasurement = TemperatureMeasurement.builder()
                .deviceId(3L)
                .startDate(date)
                .endDate(date.plusDays(1))
                .measurements(Arrays.asList(
                        MeasurementDTO.builder()
                                .timestamp(date)
                                .value(36.6)
                                .build(),
                        MeasurementDTO.builder()
                                .timestamp(date.plusDays(1))
                                .value(36.7)
                                .build()))
                .build();
        temperatureMeasurementRepository.save(temperatureMeasurement);
        assertEquals(3, temperatureMeasurementRepository.findAll().size());
    }

    @Test
    public void shouldReturnTemperatureMeasurementForDeviceId() {
        List<TemperatureMeasurement> temperatureMeasurements = temperatureMeasurementRepository.findByDeviceId(1L);
        assertEquals(1, temperatureMeasurements.size());
        TemperatureMeasurement temperatureMeasurement = temperatureMeasurements.get(0);
        assertEquals(temperatureMeasurementForDevice1, temperatureMeasurement);
    }

    @Test
    public void shouldUpdateTemperatureMeasurement() {
        TemperatureMeasurement temperatureMeasurement = temperatureMeasurementRepository.findByDeviceId(1L).get(0);
        temperatureMeasurement.setEndDate(date.plusDays(2));
        List<MeasurementDTO> measurements = temperatureMeasurement.getMeasurements();
        measurements.add(MeasurementDTO.builder()
                .timestamp(date.plusDays(2))
                .value(36.8)
                .build());
        temperatureMeasurement.setMeasurements(measurements);
        temperatureMeasurementRepository.save(temperatureMeasurement);
        assertEquals(2, temperatureMeasurementRepository.findAll().size());
        List<TemperatureMeasurement> temperatureMeasurements = temperatureMeasurementRepository.findByDeviceId(1L);
        assertEquals(1, temperatureMeasurements.size());
        TemperatureMeasurement updatedTemperatureMeasurement = temperatureMeasurements.get(0);
        assertEquals(updatedTemperatureMeasurement, temperatureMeasurement);
    }
}
