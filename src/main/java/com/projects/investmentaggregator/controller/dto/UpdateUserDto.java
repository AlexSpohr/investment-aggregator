package com.projects.investmentaggregator.controller.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDto(
        @NotBlank @Size(min = 3, message = "Username must be at least 3 characters long")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ]+(?:\\s[A-Za-zÀ-ÖØ-öø-ÿ]+)+$", message = "Invalid username format")
        String username,
        @NotBlank String password) {
}
