CREATE TABLE user_role (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           role_name ENUM('USER', 'MODERATOR') NOT NULL,
                           user_id BIGINT,
                           FOREIGN KEY (user_id) REFERENCES `user`(id) ON DELETE CASCADE
);
