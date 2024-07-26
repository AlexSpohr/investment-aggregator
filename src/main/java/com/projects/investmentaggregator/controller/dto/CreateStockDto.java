package com.projects.investmentaggregator.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateStockDto(@NotBlank String stockId, @NotBlank String description) {
}
