package com.gym.crm.module.service;

import com.gym.crm.module.dto.RegistrationResponseDto;
import com.gym.crm.module.dto.TraineeProfileDto;

import java.util.Date;

public interface TraineeService {
    RegistrationResponseDto registerTrainee(String firstName, String lastName, Date dateOfBirth, String address);
    TraineeProfileDto getTraineeProfile(String username);
    void updateTrainee(String userName, String firstName, String lastName, Date dateOfBirth, String address, Boolean isActive);
    void updateActivity(String userName, Boolean isActive);
    void deleteTrainee(String userName);
}
