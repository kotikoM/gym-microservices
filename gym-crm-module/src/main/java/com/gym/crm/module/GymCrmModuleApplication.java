package com.gym.crm.module;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gym CRM application", version = "1.0", description = "Simple customer service for gyms"))
@EnableDiscoveryClient
public class GymCrmModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymCrmModuleApplication.class, args);
    }

}
