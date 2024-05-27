package com.gym.crm.module.service.impl;

import com.gym.crm.module.DTO.TrainerWorkloadRequestDTO;
import com.gym.crm.module.DTO.ActionType;
import com.gym.crm.module.client.TrainerWorkloadClient;
import com.gym.crm.module.entity.Trainer;
import com.gym.crm.module.entity.Training;
import com.gym.crm.module.entity.User;
import com.gym.crm.module.repository.TrainerRepo;
import com.gym.crm.module.repository.TrainingRepo;
import com.gym.crm.module.repository.UserRepo;
import com.gym.crm.module.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private TrainerRepo trainerRepo;
    @Autowired
    private TrainingRepo trainingRepo;
    @Autowired
    private TrainerWorkloadClient trainerWorkloadClient;
    @Autowired
    private JmsTemplate jmsTemplate;

    public Training createTraining(Training training) {
        log.info("Creating new training: {}", training);
        callTrainerMicroService(training);
        return trainingRepo.save(training);
    }

    private void callTrainerMicroService(Training training) {
        Trainer trainer = trainerRepo.findById(training.getTrainerId().longValue())
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        User user = userRepo.findById(trainer.getUserId().longValue())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String username = user.getUsername();
        String firstName = user.getFirstname();
        String lastName = user.getLastname();
        boolean isActive = user.getIsActive();
        LocalDate date = training.getDate();
        Integer durationMin = training.getDuration();

        TrainerWorkloadRequestDTO trainerDTO = new TrainerWorkloadRequestDTO(
                username, firstName, lastName, isActive, date, durationMin, ActionType.ADD
        );

        jmsTemplate.convertAndSend("trainer-workload-queue", trainerDTO);
    }


    public List<Training> getTraineeTrainingsByCriteria(String userName, Date fromDate, Date toDate, String trainerName, Integer trainingTypeId) {
        log.info("Getting trainee trainings by criteria - UserName: {}, FromDate: {}, ToDate: {}, TrainerName: {}, TrainingTypeId: {}",
                userName, fromDate, toDate, trainerName, trainingTypeId);
        return trainingRepo.findTraineeTrainingsByCriteria(userName, fromDate, toDate, trainerName, trainingTypeId);
    }


    public List<Training> getTrainerTrainingsByCriteria(String userName, Date fromDate, Date toDate, String trainerName, Integer trainingTypeId) {
        log.info("Getting trainer trainings by criteria - UserName: {}, FromDate: {}, ToDate: {}, TrainerName: {}, TrainingTypeId: {}",
                userName, fromDate, toDate, trainerName, trainingTypeId);
        return trainingRepo.findTrainerTrainingsByCriteria(userName, fromDate, toDate, trainerName, trainingTypeId);
    }
}
