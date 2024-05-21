package com.gym.crm.module.controller;

import com.gym.crm.module.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    @Operation(summary = "User login",
            description = "Logs in the user with the provided username and password.")
    @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(mediaType = "text/plain",
                    schema = @Schema(type = "string")))
    @ApiResponse(responseCode = "401", description = "Unauthorized",
            content = @Content(mediaType = "text/plain",
                    schema = @Schema(type = "string")))
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password) {
        Boolean isAuthorized = userService.checkPassword(username, password);
        if (isAuthorized) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PutMapping("/password")
    @Operation(summary = "Change password",
            description = "Changes the password for the user with the provided username.")
    @ApiResponse(responseCode = "200", description = "Password changed successfully")
    public ResponseEntity<Void> changeLogin(
            @RequestParam String username,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        userService.updatePassword(username, oldPassword, newPassword);
        return ResponseEntity.ok().build();
    }
}
