package com.gym.crm.module.repository;

import com.gym.crm.module.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    List<User> findByFirstnameAndLastname(String firstname, String lastname);

    User findByUsername(String username);
}
