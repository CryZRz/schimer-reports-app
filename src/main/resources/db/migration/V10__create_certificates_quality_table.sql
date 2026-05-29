CREATE TABLE certificates_quality(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    product_finished_id BIGSERIAL REFERENCES products_finished(id),
    expiration_date DATE NOT NULL,
    amount INT NOT NULL
);