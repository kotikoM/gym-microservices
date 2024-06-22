package com.gym.crm.module.controller;
import com.gym.crm.module.dto.RegistrationResponseDto;
import com.gym.crm.module.dto.TraineeProfileDto;
import com.gym.crm.module.service.TraineeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/trainee")
public class TraineeController {
    @Autowired
    private TraineeService traineeService;

    @PostMapping("/register")
    @Operation(summary = "Register a new trainee",
            description = "Registers a new trainee with the provided details.")
    @ApiResponse(responseCode = "200", description = "Successful registration",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RegistrationResponseDto.class)))
    public ResponseEntity<RegistrationResponseDto> registerTrainee(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam(required = false) Date dateOfBirth,
            @RequestParam(required = false) String address) {
        RegistrationResponseDto dto = traineeService.registerTrainee(firstName, lastName, dateOfBirth, address);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/profile")
    @Operation(summary = "Get trainee profile",
            description = "Retrieves the profile of the trainee associated with the provided username.")
    @ApiResponse(responseCode = "200", description = "Trainee profile retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TraineeProfileDto.class)))
    public ResponseEntity<TraineeProfileDto> getTraineeProfile(@RequestParam String username) {
        TraineeProfileDto response = traineeService.getTraineeProfile(username);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Operation(summary = "Delete trainee",
            description = "Deletes the trainee associated with the provided username.")
    @ApiResponse(responseCode = "200", description = "Trainee deleted successfully")
    public ResponseEntity<Void> deleteTrainee(@RequestParam String username) {
        traineeService.deleteTrainee(username);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(summary = "Update trainee profile",
            description = "Updates the profile of the trainee associated with the provided username.")
    @ApiResponse(responseCode = "200", description = "Trainee profile updated successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TraineeProfileDto.class)))
    public ResponseEntity<TraineeProfileDto> updateTraineeProfile(
            @RequestParam String userName,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam Date dateOfBirth,
            @RequestParam String address,
            @RequestParam Boolean isActive) {
        traineeService.updateTrainee(userName, firstName, lastName, dateOfBirth, address, isActive);
        TraineeProfileDto response = traineeService.getTraineeProfile(userName);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/activation")
    @Operation(summary = "Activate/Deactivate trainee",
            description = "Activates or deactivates the trainee associated with the provided username.")
    @ApiResponse(responseCode = "200", description = "Trainee activation status updated successfully")
    public ResponseEntity<Void> activateTrainee(@RequestParam String username, @RequestParam Boolean isActive) {
        traineeService.updateActivity(username, isActive);
        return ResponseEntity.ok().build();
    }
}
