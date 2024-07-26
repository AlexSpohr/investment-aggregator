package com.projects.investmentaggregator.controller;

import com.projects.investmentaggregator.controller.dto.CreateStockDto;
import com.projects.investmentaggregator.controller.dto.CreateUserDto;
import com.projects.investmentaggregator.entity.User;
import com.projects.investmentaggregator.service.StockService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/v1/stock")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping()
    public ResponseEntity<Void> createStock(@Valid @RequestBody CreateStockDto createStockDto) {
        stockService.createStock(createStockDto);
        return ResponseEntity.ok().build();
    }
}
