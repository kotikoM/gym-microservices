package com.gym.crm.module.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponseDto {
    private String username;
    private String password;
}
