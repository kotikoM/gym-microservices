package com.gym.crm.microservice.repository;

import com.gym.crm.microservice.model.TrainerWorkload;

import java.util.Optional;

public interface TrainerWorkloadRepository {
    Optional<TrainerWorkload> findByUsername(String username);

    Optional<TrainerWorkload> findById(Long id);

    public void save(TrainerWorkload workload);

    public void deleteById(Long id);
}
