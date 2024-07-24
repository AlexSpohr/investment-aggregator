package com.projects.investmentaggregator.client.dto;

import java.util.List;

public record BrapiResponseDto(List<StockDto> results) {
}
