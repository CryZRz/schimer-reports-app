CREATE TABLE indicators_quality(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    receipt_date DATE NOT NULL,
    release_date DATE NOT NULL,
    status BOOLEAN NOT NULL,
    product_finished_id BIGSERIAL REFERENCES products_finished(id)
);