CREATE TABLE questions_response(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    question_id BIGSERIAL REFERENCES questions(id) NOT NULL,
    user_id BIGSERIAL REFERENCES users(id) NOT NULL,
    response VARCHAR NOT NULL
);