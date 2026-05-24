CREATE TABLE certificates_quality(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    product_finished_id BIGSERIAL REFERENCES products_finished(id),
    expiration_date DATE NOT NULL,
    amount INT NOT NULL,
    appearance VARCHAR,
    color VARCHAR,
    density VARCHAR,
    pH VARCHAR,
    solids VARCHAR,
    humidity VARCHAR,
    solubility VARCHAR,
    melting_point VARCHAR,
    concentration VARCHAR,
    pellet VARCHAR,
    material VARCHAR
);