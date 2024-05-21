package com.gym.crm.module.service.impl;

import com.gym.crm.module.DTO.RegistrationResponseDTO;
import com.gym.crm.module.DTO.TraineeProfileDTO;
import com.gym.crm.module.entity.Trainee;
import com.gym.crm.module.entity.Trainer;
import com.gym.crm.module.entity.User;
import com.gym.crm.module.generator.UserDetailsGenerator;
import com.gym.crm.module.repository.TraineeRepo;
import com.gym.crm.module.repository.TrainerRepo;
import com.gym.crm.module.repository.UserRepo;
import com.gym.crm.module.service.TraineeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TraineeRepo traineeRepo;
    @Autowired
    private TrainerRepo trainerRepo;
    @Autowired
    private UserDetailsGenerator userDetailsGenerator;

    public RegistrationResponseDTO registerTrainee(String firstName, String lastName, Date dateOfBirth, String address) {
        log.info("Registering trainee...");
        String username = userDetailsGenerator.generateUsername(firstName, lastName);
        String password = userDetailsGenerator.generatePassword();
        User user = new User(null, firstName, lastName, username, password, true);
        User savedUser = userRepo.save(user);
        Trainee trainee = new Trainee(null, dateOfBirth, address, savedUser.getId());
        traineeRepo.save(trainee);
        return new RegistrationResponseDTO(user.getUsername(), user.getPassword());
    }

    public TraineeProfileDTO getTraineeProfile(String username) {
        User user = userRepo.findByUsername(username);
        List<Trainer> trainers = trainerRepo.findTrainersOfTrainee(username);
        log.info("Trainee profile information fetched");
        return new TraineeProfileDTO(user, trainers);
    }

    public void updateTrainee(String userName, String firstName, String lastName, Date dateOfBirth, String address, Boolean isActive) {
        User user = userRepo.findByUsername(userName);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setIsActive(isActive);
        Trainee trainee = traineeRepo.findByUsername(userName);
        trainee.setDateOfBirth(dateOfBirth);
        trainee.setAddress(address);
        userRepo.save(user);
        traineeRepo.save(trainee);
        log.info("Trainee profile updated");
    }

    public void updateActivity(String userName, Boolean isActive) {
        log.info("Updating isActive status for user: {}", userName);
        User user = userRepo.findByUsername(userName);
        user.setIsActive(isActive);
    }

    public void deleteTrainee(String userName) {
        Trainee trainee = traineeRepo.findByUsername(userName);
        traineeRepo.deleteById(Long.valueOf(trainee.getId()));
    }
}
