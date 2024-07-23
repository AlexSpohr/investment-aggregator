package com.projects.investmentaggregator.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountStockId {

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "stock_id")
    private String stockId;


}
