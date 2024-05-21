package com.gym.crm.module.service.impl;

import com.gym.crm.module.DTO.RegistrationResponseDTO;
import com.gym.crm.module.DTO.TrainerProfileDTO;
import com.gym.crm.module.entity.Trainee;
import com.gym.crm.module.entity.Trainer;
import com.gym.crm.module.entity.User;
import com.gym.crm.module.generator.UserDetailsGenerator;
import com.gym.crm.module.repository.TraineeRepo;
import com.gym.crm.module.repository.TrainerRepo;
import com.gym.crm.module.repository.UserRepo;
import com.gym.crm.module.service.TrainerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TraineeRepo traineeRepo;
    @Autowired
    private TrainerRepo trainerRepo;
    @Autowired
    private UserDetailsGenerator userDetailsGenerator;

    public RegistrationResponseDTO registerTrainer(String firstName, String lastName, Integer trainingTypeId) {
        log.info("Registering trainer...");
        String username = userDetailsGenerator.generateUsername(firstName, lastName);
        String password = userDetailsGenerator.generatePassword();
        User user = new User(null, firstName, lastName, username, password, true);
        User savedUser = userRepo.save(user);
        Trainer trainer = new Trainer(null, trainingTypeId, savedUser.getId());
        trainerRepo.save(trainer);
        return new RegistrationResponseDTO(savedUser.getUsername(), savedUser.getPassword());
    }


    public List<Trainer> getUnassignedTrainers(String traineeUsername) {
        log.info("Getting unassigned trainers for trainee: {}", traineeUsername);
        return trainerRepo.findUnassignedTrainers(traineeUsername);
    }

    public TrainerProfileDTO getTrainerProfile(String username) {
        User user = userRepo.findByUsername(username);
        List<Trainee> trainees = traineeRepo.findTraineesOfTrainer(username);
        log.info("Trainer profile fetched");
        return new TrainerProfileDTO(user, trainees);
    }


    public void updateTrainer(String userName, String firstName, String lastName, Integer specialization, Boolean isActive) {
        User user = userRepo.findByUsername(userName);
        user.setFirstname(firstName);
        user.setLastname(lastName);
        user.setIsActive(isActive);
        Trainer trainer = trainerRepo.findByUsername(userName);
        trainer.setTrainingTypeId(specialization);
        userRepo.save(user);
        trainerRepo.save(trainer);
    }


    public void updateActivity(String userName, Boolean isActive) {
        log.info("Updating isActive status for user: {}", userName);
        User user = userRepo.findByUsername(userName);
        user.setIsActive(isActive);
    }

}
