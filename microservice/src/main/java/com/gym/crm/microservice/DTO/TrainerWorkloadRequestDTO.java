package com.gym.crm.microservice.DTO;

import com.gym.crm.microservice.constant.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerWorkloadRequestDTO {
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private LocalDate trainingDate;
    private Integer durationMin;
    private ActionType actionType;
}
