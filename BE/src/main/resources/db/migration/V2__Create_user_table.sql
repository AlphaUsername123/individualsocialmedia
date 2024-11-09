CREATE TABLE `user` (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        username VARCHAR(20) NOT NULL,
                        password VARCHAR(100),
                        CONSTRAINT username_length CHECK (CHAR_LENGTH(username) BETWEEN 2 AND 20)
);

