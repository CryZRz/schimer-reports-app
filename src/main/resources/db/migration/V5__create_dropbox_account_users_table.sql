CREATE TABLE dropbox_account_users(
    id BIGSERIAL PRIMARY KEY  NOT NULL,
    token VARCHAR NOT NULL,
    path VARCHAR NOT NULL,
);