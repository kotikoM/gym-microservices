package com.gym.crm.module.repository;

import com.gym.crm.module.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TraineeRepo extends JpaRepository<Trainee, Long> {

    @Query("SELECT t from Trainee t WHERE t.userId IN (SELECT u.id FROM User u WHERE u.username = :username)")
    Trainee findByUsername(String username);

    @Query("SELECT trn FROM Trainee trn WHERE trn.id IN " +
            "(SELECT trng.trainerId FROM Training trng WHERE trng.traineeId IN " +
            "(SELECT tra.id FROM Trainer tra WHERE tra.id IN " +
            "(SELECT u.id FROM User u WHERE LOWER(u.username) = LOWER(:username))))")
    List<Trainee> findTraineesOfTrainer(String username);
}
