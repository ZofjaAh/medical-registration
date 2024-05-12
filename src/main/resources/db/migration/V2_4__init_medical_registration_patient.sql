CREATE TABLE patient
(
    patient_id          SERIAL          NOT NULL,
    name                VARCHAR(32)     NOT NULL,
    surname             VARCHAR(32)     NOT NULL,
    pesel               VARCHAR(32)     NOT NULL,
    gender              VARCHAR(32),
    age                 INT,
    phone               VARCHAR(32)     NOT NULL,
    email               VARCHAR(32)     NOT NULL,
    address_id          INT             NOT NULL,
    user_id             INT             NOT NULL,
    PRIMARY KEY (patient_id),
    UNIQUE (email, pesel),
    CONSTRAINT fk_patient_address
            FOREIGN KEY (address_id)
                REFERENCES address (address_id),
    CONSTRAINT fk_patient_user
            FOREIGN KEY (user_id)
                REFERENCES medical_registration_user (user_id)
);