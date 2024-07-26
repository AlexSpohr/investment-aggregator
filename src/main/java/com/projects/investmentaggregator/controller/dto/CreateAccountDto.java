package com.projects.investmentaggregator.controller.dto;

import com.projects.investmentaggregator.entity.Account;
import com.projects.investmentaggregator.entity.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.NotBlank;

public record CreateAccountDto(@NotBlank String description,
                               @NotBlank String street,
                               @NotNull @Positive Integer number) {
}
