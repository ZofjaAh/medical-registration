CREATE TABLE medical_record
(
    medical_record_id    SERIAL                   NOT NULL,
    medical_record_code  VARCHAR(64)              NOT NULL,
    comment_doctor       TEXT                     NOT NULL,
    diagnosis            VARCHAR(64)              NOT NULL,
    date_time            TIMESTAMP WITH TIME ZONE NOT NULL,
    appointment_id       INT                      NOT NULL,
    PRIMARY KEY (medical_record_id),
    UNIQUE (medical_record_code),
    CONSTRAINT fk_medical_record_appointment
                FOREIGN KEY (appointment_id)
                    REFERENCES appointment (appointment_id)
);