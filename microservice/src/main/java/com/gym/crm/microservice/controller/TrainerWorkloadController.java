package com.gym.crm.microservice.controller;

import com.gym.crm.microservice.DTO.TrainerWorkloadRequestDTO;
import com.gym.crm.microservice.model.TrainerWorkload;
import com.gym.crm.microservice.service.TrainerWorkloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainers")
public class TrainerWorkloadController {
    @Autowired
    private TrainerWorkloadService trainerWorkloadService;


    @GetMapping("/{username}")
    public ResponseEntity<TrainerWorkload> workSummary(@PathVariable("username") String username) {
        return ResponseEntity.ok(trainerWorkloadService.findByUsername(username));
    }

    @PostMapping("/{username}")
    public ResponseEntity<Void> handleTrainerWorkload(@PathVariable("username") String username,
                                                      @RequestBody TrainerWorkloadRequestDTO trainerWorkloadRequestDTO) {
        try {
            trainerWorkloadService.handleTrainerWorkload(trainerWorkloadRequestDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
