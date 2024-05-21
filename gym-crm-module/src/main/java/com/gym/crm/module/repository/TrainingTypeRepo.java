package com.gym.crm.module.repository;

import com.gym.crm.module.entity.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingTypeRepo extends JpaRepository<TrainingType, Long> {

}
