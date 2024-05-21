package com.gym.crm.module.DTO;

import com.gym.crm.module.entity.Trainee;
import com.gym.crm.module.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TrainerProfileDTO {
    private User user;
    private List<Trainee> trainees;
}
