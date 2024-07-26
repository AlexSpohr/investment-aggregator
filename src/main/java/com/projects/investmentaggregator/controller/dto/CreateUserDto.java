package com.projects.investmentaggregator.controller.dto;

import com.projects.investmentaggregator.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;



public record CreateUserDto(
        @NotBlank @Size(min = 3, message = "Username must be at least 3 characters long")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)+$", message = "Invalid username format")
        String username,
        @Email @NotBlank String email,
        @NotBlank String password) {

    public User toUser() {
        return new User(
            username,
                email,
                password
        );
    }
}
