package com.gym.crm.module.dto;

import com.gym.crm.module.entity.Trainer;
import com.gym.crm.module.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TraineeProfileDto {
    private User user;
    private List<Trainer> trainers;
}
