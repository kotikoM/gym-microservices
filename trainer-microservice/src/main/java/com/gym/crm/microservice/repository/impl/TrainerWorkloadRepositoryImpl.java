package com.gym.crm.microservice.repository.impl;

import com.gym.crm.microservice.model.TrainerWorkload;
import com.gym.crm.microservice.repository.TrainerWorkloadRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TrainerWorkloadRepositoryImpl implements TrainerWorkloadRepository {
    private final Map<Long, TrainerWorkload> trainerWorkloadMap = new HashMap<>();

    public Optional<TrainerWorkload> findByUsername(String username) {
        return trainerWorkloadMap.values()
                .stream()
                .filter(t -> t.getUsername().equals(username))
                .findFirst();
    }

    public Optional<TrainerWorkload> findById(Long id) {
        return Optional.ofNullable(trainerWorkloadMap.get(id));
    }

    public void save(TrainerWorkload workload) {
        trainerWorkloadMap.put(workload.getId(), workload);
    }

    public void deleteById(Long id) {
        trainerWorkloadMap.remove(id);
    }
}
