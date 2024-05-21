package com.gym.crm.module.repository;

import com.gym.crm.module.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface TrainingRepo extends JpaRepository<Training, Long> {
    @Query("SELECT t FROM Training t WHERE t.traineeId = " +
            "(SELECT tr.id FROM Trainee tr INNER JOIN User u ON tr.userId=u.id WHERE u.username = :username) " +
            "AND t.date >= :fromDate AND t.date <= :toDate " +
            "AND t.trainerId IN (SELECT tr.id FROM Trainer tr INNER JOIN User u ON tr.userId=u.id WHERE u.username = :trainerName) " +
            "AND t.trainingTypeId = :trainingTypeId")
    List<Training> findTraineeTrainingsByCriteria(String username, Date fromDate, Date toDate, String trainerName, Integer trainingTypeId);

    @Query("SELECT t FROM Training t WHERE t.trainerId = " +
            "(SELECT tr.id FROM Trainer tr INNER JOIN User u ON tr.userId=u.id WHERE u.username = :username) " +
            "AND t.date >= :fromDate AND t.date <= :toDate " +
            "AND t.trainerId IN (SELECT tr.id FROM Trainer tr INNER JOIN User u ON tr.userId=u.id WHERE u.username = :trainerName) " +
            "AND t.trainingTypeId = :trainingTypeId")
    List<Training> findTrainerTrainingsByCriteria(String username, Date fromDate, Date toDate, String trainerName, Integer trainingTypeId);
}
