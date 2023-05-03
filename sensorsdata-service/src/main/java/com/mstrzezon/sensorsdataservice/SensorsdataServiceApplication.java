package com.mstrzezon.sensorsdataservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "SensorsData API", version = "1.0", description = "Documentation SensorsData API v1.0")
)
public class SensorsdataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorsdataServiceApplication.class, args);
	}

}
