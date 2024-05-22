package com.gym.crm.microservice.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySummary {
    private Long id;
    private Month month;
    private Integer workMinutes;
}
