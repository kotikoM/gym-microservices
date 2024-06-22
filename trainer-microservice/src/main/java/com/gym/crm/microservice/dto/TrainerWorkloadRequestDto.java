package com.gym.crm.microservice.dto;

import com.gym.crm.microservice.constant.ActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TrainerWorkloadRequestDto implements Serializable{
    private String username;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private LocalDate trainingDate;
    private Integer durationMin;
    private ActionType actionType;
}
