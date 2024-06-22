package com.gym.crm.module.service;

import com.gym.crm.module.dto.RegistrationResponseDto;
import com.gym.crm.module.dto.TrainerProfileDto;
import com.gym.crm.module.entity.Trainer;

import java.util.List;

public interface TrainerService {
    RegistrationResponseDto registerTrainer(String firstName, String lastName, Integer trainingTypeId);
    List<Trainer> getUnassignedTrainers(String traineeUsername);
    TrainerProfileDto getTrainerProfile(String username);
    void updateTrainer(String userName, String firstName, String lastName, Integer specialization, Boolean isActive);
    void updateActivity(String userName, Boolean isActive);

}
