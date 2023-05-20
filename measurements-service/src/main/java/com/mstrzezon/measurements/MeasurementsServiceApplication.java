package com.mstrzezon.measurements;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Measurements API", version = "1.0", description = "Documentation Measurements API v1.0")
)
public class MeasurementsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeasurementsServiceApplication.class, args);
	}

}
