package com.mstrzezon.deviceservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(title = "Device API", version = "1.0", description = "Documentation Device API v1.0")
)
public class DeviceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeviceServiceApplication.class, args);
	}

}
