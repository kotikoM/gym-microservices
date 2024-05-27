package com.gym.crm.module.client;

import com.gym.crm.module.DTO.TrainerWorkloadRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "TRAINER-MICROSERVICE")
public interface TrainerWorkloadClient {
    @PostMapping("/trainers")
    ResponseEntity<Void> handleTrainerWorkload(@RequestBody TrainerWorkloadRequestDTO trainerWorkloadRequestDTO);
}
