CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    job_position VARCHAR(50) NOT NULL,
    signature TEXT NOT NULL,
    password VARCHAR NOT NULL,
    email VARCHAR NOT NULL UNIQUE,
    rol_id BIGSERIAL REFERENCES roles(id) NOT NULL,
    email_account_id BIGSERIAL REFERENCES email_account_users(id) NOT NULL,
    dropbox_account_id BIGSERIAL REFERENCES dropbox_account_users(id) NOT NULL,
    department_id BIGSERIAL REFERENCES departments(id) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);