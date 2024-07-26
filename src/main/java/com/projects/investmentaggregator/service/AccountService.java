package com.projects.investmentaggregator.service;

import com.projects.investmentaggregator.client.BrapiClient;
import com.projects.investmentaggregator.controller.dto.AccountStockResponseDto;
import com.projects.investmentaggregator.controller.dto.AssociateAccountStockDto;
import com.projects.investmentaggregator.entity.AccountStock;
import com.projects.investmentaggregator.entity.AccountStockId;
import com.projects.investmentaggregator.exception.AccountNotFoundException;
import com.projects.investmentaggregator.exception.BrapiErrorException;
import com.projects.investmentaggregator.exception.StockNotFoundException;
import com.projects.investmentaggregator.repository.AccountRepository;
import com.projects.investmentaggregator.repository.AccountStockRepository;
import com.projects.investmentaggregator.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AccountService {

    @Value("#{environment.TOKEN}")
    private String TOKEN;
    private final AccountRepository accountRepository;
    private final StockRepository stockRepository;
    private final AccountStockRepository accountStockRepository;
    private final BrapiClient brapiClient;

    public AccountService(AccountRepository accountRepository, StockRepository stockRepository, AccountStockRepository accountStockRepository, BrapiClient brapiClient) {
        this.accountRepository = accountRepository;
        this.stockRepository = stockRepository;
        this.accountStockRepository = accountStockRepository;
        this.brapiClient = brapiClient;
    }

    @Transactional
    public void associateStock(String accountId, AssociateAccountStockDto associateDto) {

        var account = accountRepository.findById(Long.parseLong(accountId))
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        var stock = stockRepository.findById(associateDto.stockId())
                .orElseThrow(() -> new StockNotFoundException(associateDto.stockId()));

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

    @Transactional(readOnly = true)
    public List<AccountStockResponseDto> listStocks(String accountId) {

        var account = accountRepository.findById(Long.parseLong(accountId))
                .orElseThrow(() -> new AccountNotFoundException(accountId));

        return account.getAccountStocks()
                .stream()
                .map(accountStock -> new AccountStockResponseDto(
                        accountStock.getStock().getStockId(),
                        accountStock.getQuantity(),
                        getTotal(accountStock.getQuantity() ,accountStock.getStock().getStockId())))
                .toList();
    }

    private double getTotal(Integer quantity, String stockId) {

        try {
            var response = brapiClient.getQuote(TOKEN, stockId);
            var price = response.results().get(0).regularMarketPrice();
            return price * quantity;
        } catch (Exception e) {
            throw new BrapiErrorException(stockId);
        }
    }
}
