CREATE TABLE speciality
(
    speciality_id    SERIAL          NOT NULL,
    speciality_code  VARCHAR(32)     NOT NULL,
    definition          VARCHAR(32)     NOT NULL,
    PRIMARY KEY (speciality_id),
    UNIQUE (speciality_code)
);