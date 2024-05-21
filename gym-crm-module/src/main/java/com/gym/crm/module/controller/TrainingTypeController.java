package com.gym.crm.module.controller;

import com.gym.crm.module.entity.TrainingType;
import com.gym.crm.module.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/trainingtype")
public class TrainingTypeController {
    @Autowired
    private TrainingTypeService trainingTypeService;

    @GetMapping
    @Operation(summary = "Get all training types",
            description = "Retrieves all available training types.")
    @ApiResponse(responseCode = "200", description = "Training types retrieved successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = TrainingType.class)))
    public ResponseEntity<List<TrainingType>> getAllTrainingTypes() {
        List<TrainingType> allTrainingTypes = trainingTypeService.getAllTrainingTypes();
        return ResponseEntity.ok(allTrainingTypes);
    }
}
