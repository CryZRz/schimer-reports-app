CREATE TABLE raw_material_releases(
    id BIGSERIAL PRIMARY KEY  NOT NULL,
    expiration_date DATE NOT NULL,
    amount INT NOT NULL,
    note TEXT,
    is_accepted BOOLEAN NOT NULL,
    certificate VARCHAR NOT NULL,
    raw_material_id BIGSERIAL REFERENCES raw_materials(id) NOT NULL
)