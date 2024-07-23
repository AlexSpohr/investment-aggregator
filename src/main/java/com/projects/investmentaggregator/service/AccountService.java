package com.projects.investmentaggregator.service;

import com.projects.investmentaggregator.controller.dto.AssociateAccountStockDto;
import com.projects.investmentaggregator.entity.AccountStock;
import com.projects.investmentaggregator.entity.AccountStockId;
import com.projects.investmentaggregator.repository.AccountRepository;
import com.projects.investmentaggregator.repository.AccountStockRepository;
import com.projects.investmentaggregator.repository.StockRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
    }

    public void associateStock(String accountId, AssociateAccountStockDto associateDto) {

        var account = accountRepository.findById(Long.parseLong(accountId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var stock = stockRepository.findById(associateDto.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var id = new AccountStockId(
                account.getAccountId(),
                stock.getStockId());

        var entity = new AccountStock(
                id,
                account,
                stock,
                associateDto.quantity()
        );

        accountStockRepository.save(entity);
    }
}
