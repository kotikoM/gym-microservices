package com.gym.crm.microservice.service;

import com.gym.crm.microservice.DTO.TrainerWorkloadRequestDTO;
import com.gym.crm.microservice.model.TrainerWorkload;

import java.util.List;

public interface TrainerWorkloadService {

    List<TrainerWorkload> findAll();

    TrainerWorkload findByUsername(String username);

    void handleTrainerWorkload(TrainerWorkloadRequestDTO trainerWorkloadRequestDTO);

}
