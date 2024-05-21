package com.gym.crm.module.service;

import com.gym.crm.module.entity.Training;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    Training createTraining(Training training);
    List<Training> getTraineeTrainingsByCriteria(String userName, Date fromDate, Date toDate, String trainerName, Integer trainingTypeId);
    List<Training> getTrainerTrainingsByCriteria(String userName, Date fromDate, Date toDate, String trainerName, Integer trainingTypeId);
}
