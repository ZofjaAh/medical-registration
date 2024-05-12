CREATE TABLE doctor
(
    doctor_id           SERIAL          NOT NULL,
    doctor_code         VARCHAR(64)     NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    surname             VARCHAR(32)     NOT NULL,
    pesel               VARCHAR(32)     NOT NULL,
    email               VARCHAR(32)     NOT NULL,
    address_id          INT             NOT NULL,
    speciality_id    INT             NOT NULL,
    user_id             INT             NOT NULL,
    PRIMARY KEY (doctor_id),
    UNIQUE (pesel),
    CONSTRAINT fk_doctor_address
            FOREIGN KEY (address_id)
                REFERENCES address (address_id),
    CONSTRAINT fk_doctor_speciality
            FOREIGN KEY (speciality_id)
                REFERENCES speciality (speciality_id),
    CONSTRAINT fk_doctor_user
            FOREIGN KEY (user_id)
                REFERENCES medical_registration_user (user_id)
);