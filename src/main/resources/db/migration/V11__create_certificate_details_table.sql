CREATE TABLE certificates_quality_details(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    certificate_quality_id BIGINT REFERENCES certificates_quality(id) NOT NULL,
    specification_name VARCHAR NOT NULL,
    parameter_value VARCHAR NOT NULL,
    result_value VARCHAR NOT NULL,
    units_value VARCHAR NOT NULL,
    methodology_value VARCHAR NOT NULL
);