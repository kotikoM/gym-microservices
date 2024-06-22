package com.gym.crm.microservice.controller;

import com.gym.crm.microservice.dto.TrainerWorkloadRequestDto;
import com.gym.crm.microservice.model.TrainerWorkload;
import com.gym.crm.microservice.service.TrainerWorkloadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/trainers")
@Slf4j
public class TrainerWorkloadController {
    @Autowired
    private TrainerWorkloadService trainerWorkloadService;

    @GetMapping()
    public ResponseEntity<List<TrainerWorkload>> findAll() {
        log.info("Getting every trainer workload");
        return ResponseEntity.ok(trainerWorkloadService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<TrainerWorkload> workSummary(@PathVariable("username") String username) {
        log.info("GET method called for username: {}", username);

        Optional<TrainerWorkload> trainerWorkloadOpt = trainerWorkloadService.findByUsername(username);
        if (trainerWorkloadOpt.isPresent()) {
            return ResponseEntity.ok(trainerWorkloadOpt.get());
        } else {
            log.warn("TrainerWorkload not found for username: {}", username);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Void> handleTrainerWorkload(@RequestBody TrainerWorkloadRequestDto trainerWorkloadRequestDTO) {
        try {
            log.info("POST method called with payload: {}", trainerWorkloadRequestDTO);
            trainerWorkloadService.handleTrainerWorkload(trainerWorkloadRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error handling trainer workload for payload: {}", trainerWorkloadRequestDTO, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
