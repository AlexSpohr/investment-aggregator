package com.projects.investmentaggregator.controller.dto;


import jakarta.validation.constraints.NotBlank;

public record UpdateUserDto(
        @NotBlank String username,
        @NotBlank String password
) {

}
