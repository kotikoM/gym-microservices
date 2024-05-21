package com.gym.crm.module.service;

import com.gym.crm.module.DTO.RegistrationResponseDTO;
import com.gym.crm.module.DTO.TrainerProfileDTO;
import com.gym.crm.module.entity.Trainer;

import java.util.List;

public interface TrainerService {
    RegistrationResponseDTO registerTrainer(String firstName, String lastName, Integer trainingTypeId);
    List<Trainer> getUnassignedTrainers(String traineeUsername);
    TrainerProfileDTO getTrainerProfile(String username);
    void updateTrainer(String userName, String firstName, String lastName, Integer specialization, Boolean isActive);
    void updateActivity(String userName, Boolean isActive);

}
