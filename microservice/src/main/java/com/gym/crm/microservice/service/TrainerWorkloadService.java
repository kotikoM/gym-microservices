package com.gym.crm.microservice.service;

import com.gym.crm.microservice.DTO.TrainerWorkloadRequestDTO;
import com.gym.crm.microservice.model.TrainerWorkload;

public interface TrainerWorkloadService {

    TrainerWorkload findByUsername(String username);

    void handleTrainerWorkload(TrainerWorkloadRequestDTO trainerWorkloadRequestDTO);

}
