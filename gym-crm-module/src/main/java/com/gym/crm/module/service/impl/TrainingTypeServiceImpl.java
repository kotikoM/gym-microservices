package com.gym.crm.module.service.impl;


import com.gym.crm.module.entity.TrainingType;
import com.gym.crm.module.repository.TrainingTypeRepo;
import com.gym.crm.module.service.TrainingTypeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TrainingTypeServiceImpl implements TrainingTypeService {
    @Autowired
    private TrainingTypeRepo trainingTypeRepo;

    public List<TrainingType> getAllTrainingTypes() {
        log.info("Fetching all training types...");
        return trainingTypeRepo.findAll();
    }
}
