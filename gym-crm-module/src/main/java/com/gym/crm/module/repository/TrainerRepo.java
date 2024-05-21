package com.gym.crm.module.repository;

import com.gym.crm.module.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TrainerRepo extends JpaRepository<Trainer, Long> {
    @Query("SELECT trn FROM Trainer trn WHERE trn.id IN " +
            "(SELECT trng.trainerId from Training trng WHERE trng.traineeId IN " +
            "(SELECT tra.id from Trainee tra WHERE tra.id IN " +
            "(SELECT u.id FROM User u WHERE u.username = :username)))")
    List<Trainer> findTrainersOfTrainee(@Param("username") String username);
    @Query("SELECT trn FROM Trainer trn WHERE trn.id NOT IN " +
            "(SELECT trng.trainerId from Training trng WHERE trng.traineeId IN " +
            "(SELECT tra.id from Trainee tra WHERE tra.id IN " +
            "(SELECT u.id FROM User u WHERE u.username = :username)))")
    List<Trainer> findUnassignedTrainers(@Param("username") String username);

    @Query("SELECT t from Trainer t WHERE t.userId IN (SELECT u.id FROM User u WHERE u.username = :username)")
    Trainer findByUsername(String username);
}
