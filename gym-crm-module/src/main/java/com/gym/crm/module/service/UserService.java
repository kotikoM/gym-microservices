package com.gym.crm.module.service;

import com.gym.crm.module.entity.User;

public interface UserService {
    User getByUsername(String username);
    void updatePassword(String userName, String oldPassword, String newPassword);
    Boolean checkPassword(String userName, String password);
}
