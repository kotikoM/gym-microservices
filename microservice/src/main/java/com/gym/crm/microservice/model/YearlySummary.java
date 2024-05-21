package com.gym.crm.microservice.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Year;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearlySummary {
    private Long id;
    private Year year;
    private Set<MonthlySummary> monthlySummaries;
}
