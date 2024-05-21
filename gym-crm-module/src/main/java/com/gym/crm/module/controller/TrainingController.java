package com.gym.crm.module.controller;

import com.gym.crm.module.entity.Training;
import com.gym.crm.module.service.TrainingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/training")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    @PostMapping
    @Operation(summary = "Create training",
            description = "Creates a new training with the provided details.")
    @ApiResponse(responseCode = "200", description = "Training created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Training.class)))
    public ResponseEntity<Training> createTraining(@RequestBody Training training) {
        Training createdTraining = trainingService.createTraining(training);
        return ResponseEntity.ok(createdTraining);
    }

    @GetMapping("/trainee")
    @Operation(summary = "Get trainee trainings",
            description = "Retrieves trainings for the trainee based on specified criteria.")
    @ApiResponse(responseCode = "200", description = "Trainee trainings retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Training.class)))
    public ResponseEntity<List<Training>> getTraineeTrainings(
            @RequestParam String userName,
            @RequestParam Date fromDate,
            @RequestParam Date toDate,
            @RequestParam String trainerName,
            @RequestParam Integer trainingTypeId) {
        List<Training> traineeTrainingsByCriteria = trainingService.getTraineeTrainingsByCriteria(userName, fromDate, toDate, trainerName, trainingTypeId);
        return ResponseEntity.ok(traineeTrainingsByCriteria);
    }

    @GetMapping("/trainer")
    @Operation(summary = "Get trainer trainings",
            description = "Retrieves trainings for the trainer based on specified criteria.")
    @ApiResponse(responseCode = "200", description = "Trainer trainings retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Training.class)))
    public ResponseEntity<List<Training>> getTrainerTrainings(
            @RequestParam String userName,
            @RequestParam Date fromDate,
            @RequestParam Date toDate,
            @RequestParam String trainerName,
            @RequestParam Integer trainingTypeId) {
        List<Training> trainerTrainingsByCriteria = trainingService.getTrainerTrainingsByCriteria(userName, fromDate, toDate, trainerName, trainingTypeId);
        return ResponseEntity.ok(trainerTrainingsByCriteria);
    }
}
