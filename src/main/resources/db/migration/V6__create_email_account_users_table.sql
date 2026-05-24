CREATE TABLE email_account_users(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL,
    url VARCHAR NOT NULL,
    port INT NOT NULL
);