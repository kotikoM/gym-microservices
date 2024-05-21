package com.gym.crm.module.generator;

import com.gym.crm.module.entity.User;
import com.gym.crm.module.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.List;

@Component
public class UserDetailsGenerator {
    @Autowired
    private UserRepo userRepo;

    public String generateUsername(String firstname, String lastname) {
        List<User> usersSameFirstLastName = userRepo.findByFirstnameAndLastname(firstname, lastname);
        return firstname +
                "." +
                lastname +
                usersSameFirstLastName.size();
    }

    public String generatePassword() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder password = new StringBuilder();
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }
}
