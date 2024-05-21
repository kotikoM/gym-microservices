package com.gym.crm.module.DTO;

import com.gym.crm.module.entity.Trainer;
import com.gym.crm.module.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TraineeProfileDTO {
    private User user;
    private List<Trainer> trainers;
}
