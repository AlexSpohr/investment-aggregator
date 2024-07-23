CREATE TABLE IF NOT EXISTS tb_accountstock (
                                               account_id BIGINT,
                                               stock_id VARCHAR(255),
    quantity INT,
    PRIMARY KEY (account_id, stock_id),
    FOREIGN KEY (account_id) REFERENCES tb_accounts(account_id),
    FOREIGN KEY (stock_id) REFERENCES tb_stock(stock_id)
    );
