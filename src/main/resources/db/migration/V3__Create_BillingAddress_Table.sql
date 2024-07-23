CREATE TABLE IF NOT EXISTS tb_billingaddress (
                                                 account_id BIGINT PRIMARY KEY,
                                                 street VARCHAR(255),
    number INT,
    FOREIGN KEY (account_id) REFERENCES tb_accounts(account_id)
    );