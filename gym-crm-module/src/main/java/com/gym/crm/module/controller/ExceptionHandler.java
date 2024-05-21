package com.gym.crm.module.controller;

import com.gym.crm.module.exception.UserCreationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(UserCreationException.class)
    public ResponseEntity<String> userCreationException(UserCreationException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
