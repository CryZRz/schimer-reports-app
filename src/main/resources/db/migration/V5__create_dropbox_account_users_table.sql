CREATE TABLE dropbox_account_users(
    id BIGSERIAL PRIMARY KEY  NOT NULL,
    email VARCHAR NOT NULL,
    password VARCHAR NOT NULL
);