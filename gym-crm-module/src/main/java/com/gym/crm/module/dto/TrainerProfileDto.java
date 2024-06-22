package com.gym.crm.module.dto;

import com.gym.crm.module.entity.Trainee;
import com.gym.crm.module.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrainerProfileDto {
    private User user;
    private List<Trainee> trainees;
}
