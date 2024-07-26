package com.projects.investmentaggregator.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AssociateAccountStockDto(@NotBlank String stockId, @NotNull @Min(1) @Positive int quantity) {
}
