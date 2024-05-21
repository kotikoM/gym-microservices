package com.gym.crm.module.service.impl;

import com.gym.crm.module.entity.Training;
import com.gym.crm.module.repository.TrainingRepo;
import com.gym.crm.module.service.TrainingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class TrainingServiceImpl implements TrainingService {
    
    @Autowired
    private TrainingRepo trainingRepo;

    public Training createTraining(Training training) {
        log.info("Creating new training: {}", training);
        return trainingRepo.save(training);
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
