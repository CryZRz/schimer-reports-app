CREATE TABLE quality_parameters_raw_material(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    parameter VARCHAR NOT NULL,
    specification VARCHAR NOT NULL,
    result VARCHAR NOT NULL,
    raw_material_release_id BIGSERIAL REFERENCES raw_material_releases(id)
);