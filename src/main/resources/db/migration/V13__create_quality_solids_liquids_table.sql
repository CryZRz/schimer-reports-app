CREATE TABLE quality_solids_liquids(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    solids NUMERIC(10,2) NOT NULL,
    ph NUMERIC(4,2) NOT NULL,
    apparent_density NUMERIC(10,3) NOT NULL,
    appearance VARCHAR,
    zinc_oxide_percentage NUMERIC(5,2),
    kilograms NUMERIC(10,2) NOT NULL,
    identification_review BOOLEAN DEFAULT FALSE,
    packaging_review BOOLEAN DEFAULT FALSE,
    certificate INT,
    product_finished_id BIGSERIAL REFERENCES products_finished(id)
);