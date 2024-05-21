package com.gym.crm.module.controller;

import com.gym.crm.module.DTO.RegistrationResponseDTO;
import com.gym.crm.module.DTO.TrainerProfileDTO;
import com.gym.crm.module.entity.Trainer;
import com.gym.crm.module.service.TrainerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/trainer")
public class TrainerController {
    @Autowired
    private TrainerService trainerService;

    @PostMapping("/register")
    @Operation(summary = "Register a new trainer",
            description = "Registers a new trainer with the provided details.")
    @ApiResponse(responseCode = "200", description = "Successful registration",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RegistrationResponseDTO.class)))
    public ResponseEntity<RegistrationResponseDTO> registerTrainer(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Integer trainingTypeId) {
        RegistrationResponseDTO dto = trainerService.registerTrainer(firstName, lastName, trainingTypeId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/profile")
    @Operation(summary = "Get trainer profile",
            description = "Retrieves the profile of the trainer associated with the provided username.")
    @ApiResponse(responseCode = "200", description = "Trainer profile retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TrainerProfileDTO.class)))
    public ResponseEntity<TrainerProfileDTO> getTrainerProfile(@RequestParam String username) {
        TrainerProfileDTO response = trainerService.getTrainerProfile(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unassigned")
    @Operation(summary = "Get unassigned trainers",
            description = "Retrieves a list of trainers who are currently unassigned to any trainee.")
    @ApiResponse(responseCode = "200", description = "List of unassigned trainers retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Trainer.class)))
    public ResponseEntity<List<Trainer>> getUnassignedTrainers(@RequestParam String traineeUsername) {
        List<Trainer> trainers = trainerService.getUnassignedTrainers(traineeUsername);
        return ResponseEntity.ok(trainers);
    }

    @PutMapping
    @Operation(summary = "Update trainer profile",
            description = "Updates the profile of the trainer associated with the provided username.")
    @ApiResponse(responseCode = "200", description = "Trainer profile updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TrainerProfileDTO.class)))
    public ResponseEntity<TrainerProfileDTO> updateTrainerProfile(
            @RequestParam String userName,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Integer specialization,
            @RequestParam Boolean isActive) {
        trainerService.updateTrainer(userName, firstName, lastName, specialization, isActive);
        TrainerProfileDTO trainerProfile = trainerService.getTrainerProfile(userName);
        return ResponseEntity.ok(trainerProfile);
    }

    @PatchMapping("/activation")
    @Operation(summary = "Activate/Deactivate trainer",
            description = "Activates or deactivates the trainer associated with the provided username.")
    @ApiResponse(responseCode = "200", description = "Trainer activation status updated successfully")
    public ResponseEntity<Void> activateTrainer(@RequestParam String username, @RequestParam Boolean isActive) {
        trainerService.updateActivity(username, isActive);
        return ResponseEntity.ok().build();
    }
}
