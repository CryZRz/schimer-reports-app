CREATE TABLE raw_materials(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    release_date DATE NOT NULL,
    batch VARCHAR NOT NULL,
    folio VARCHAR NOT NULL,
    product VARCHAR NOT NULL,
    status BOOLEAN NOT NULL,
    created_by BIGSERIAL REFERENCES users(id) NOT NULL
)