package com.gym.crm.module.service.impl;

import com.gym.crm.module.entity.User;
import com.gym.crm.module.repository.UserRepo;
import com.gym.crm.module.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    public User getByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public void updatePassword(String userName, String oldPassword, String newPassword) {
        User user = userRepo.findByUsername(userName);

        if (checkPassword(userName, oldPassword)) {
            log.info("Updating user password");
            user.setPassword(newPassword);
            userRepo.save(user);
        } else {
            log.warn("Wrong password");
        }
    }

    public Boolean checkPassword(String userName, String password) {
        User user = userRepo.findByUsername(userName);
        return user.getPassword().equals(password);
    }
}
