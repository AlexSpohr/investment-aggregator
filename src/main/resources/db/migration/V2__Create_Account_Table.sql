CREATE TABLE IF NOT EXISTS tb_accounts (
                                           account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           description VARCHAR(255),
    user_id VARCHAR(36),
    FOREIGN KEY (user_id) REFERENCES tb_users(user_id)
    );