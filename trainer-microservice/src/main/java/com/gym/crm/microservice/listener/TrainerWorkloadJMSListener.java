package com.gym.crm.microservice.listener;

import com.gym.crm.microservice.DTO.TrainerWorkloadRequestDTO;
import com.gym.crm.microservice.service.TrainerWorkloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TrainerWorkloadJMSListener {
    @Autowired
    private TrainerWorkloadService trainerWorkloadService;

    @JmsListener(destination = "trainer-workload-queue")
    public void handleTrainerWorkload(TrainerWorkloadRequestDTO trainerDTO) {
        try {
            log.info("Received TrainerWorkloadRequestDTO: {}", trainerDTO);
            trainerWorkloadService.handleTrainerWorkload(trainerDTO);
        } catch (Exception e) {
            log.error("Error processing TrainerWorkloadRequestDTO: {}", e.getMessage(), e);
        }
    }
}
