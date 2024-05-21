package com.gym.crm.module.service;

import com.gym.crm.module.DTO.RegistrationResponseDTO;
import com.gym.crm.module.DTO.TraineeProfileDTO;

import java.util.Date;

public interface TraineeService {
    RegistrationResponseDTO registerTrainee(String firstName, String lastName, Date dateOfBirth, String address);
    TraineeProfileDTO getTraineeProfile(String username);
    void updateTrainee(String userName, String firstName, String lastName, Date dateOfBirth, String address, Boolean isActive);
    void updateActivity(String userName, Boolean isActive);
    void deleteTrainee(String userName);
}
