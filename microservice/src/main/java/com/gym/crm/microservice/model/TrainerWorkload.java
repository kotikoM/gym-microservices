package com.gym.crm.microservice.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainerWorkload {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Boolean status;
    private Set<YearlySummary> yearlySummaries;
}
