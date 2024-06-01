package com.gym.crm.microservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

import java.util.Set;

@Data
@Document(collection = "TrainerWorkload")
public class TrainerWorkload {

    @Id
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private boolean status;
    private Set<YearlySummary> years;
}

