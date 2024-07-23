package com.projects.investmentaggregator.repository;

import com.projects.investmentaggregator.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}
