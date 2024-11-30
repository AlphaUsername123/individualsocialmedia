CREATE TABLE user_ban_entity
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    reason    VARCHAR(255),
    ban_until TIMESTAMP
);