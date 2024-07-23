package com.projects.investmentaggregator.repository;

import com.projects.investmentaggregator.entity.AccountStock;
import com.projects.investmentaggregator.entity.AccountStockId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
