package com.gym.crm.module;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gym CRM application", version = "1.0", description = "Simple customer service for gyms"))
@EnableDiscoveryClient
@EnableFeignClients
public class GymCrmModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymCrmModuleApplication.class, args);
    }

}
