CREATE TABLE users (
--    id                 BIGINT AUTO_INCREMENT, -- H2
    id                 SERIAL PRIMARY KEY NOT NULL,
    username           VARCHAR(255) UNIQUE,
    password           VARCHAR(255)
);