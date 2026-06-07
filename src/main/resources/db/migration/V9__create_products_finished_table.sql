CREATE TABLE products_finished(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    batch VARCHAR NOT NULL,
    folio VARCHAR NOT NULL,
    product VARCHAR NOT NULL,
    report_path VARCHAR NOT NULL,
    created_by BIGSERIAL REFERENCES users(id) NOT NULL,
    template_id BIGSERIAL REFERENCES template_finished_products(id),
    created_at DATE NOT NULL
);