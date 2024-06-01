package com.gym.crm.microservice.model;

import lombok.Data;

import java.util.Set;

@Data
public class YearlySummary {
    private Integer year;
    private Set<MonthlySummary> months;
}
