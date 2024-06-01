package com.gym.crm.microservice.repository;

import com.gym.crm.microservice.model.TrainerWorkload;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerWorkloadRepositoryMongo extends MongoRepository<TrainerWorkload, Integer> {
    Optional<TrainerWorkload> findByUsername(String username);
}
