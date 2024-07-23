package com.projects.investmentaggregator.service;

import com.projects.investmentaggregator.controller.dto.CreateStockDto;
import com.projects.investmentaggregator.entity.Stock;
import com.projects.investmentaggregator.repository.StockRepository;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public void createStock(CreateStockDto createStockDto) {

        var stock = new Stock(
                createStockDto.stockId(),
                createStockDto.description()
        );

        stockRepository.save(stock);
    }
}
