package com.gym.crm.microservice.service;

import com.gym.crm.microservice.dto.TrainerWorkloadRequestDto;
import com.gym.crm.microservice.model.TrainerWorkload;

import java.util.List;
import java.util.Optional;

public interface TrainerWorkloadService {

    List<TrainerWorkload> findAll();

    Optional<TrainerWorkload> findByUsername(String username);

    void handleTrainerWorkload(TrainerWorkloadRequestDto trainerWorkloadRequestDTO);

}
