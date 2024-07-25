package com.projects.investmentaggregator.controller.dto;

import com.projects.investmentaggregator.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank String username,
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
