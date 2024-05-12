CREATE TABLE appointment
(
    appointment_id    SERIAL                   NOT NULL,
    appointment_code  VARCHAR(64)              NOT NULL,
    comment_patient   TEXT                     NOT NULL,
    execution         BOOLEAN                  NOT NULL,
    schedule_id       INT                      NOT NULL,
    patient_id        INT                      NOT NULL,
    PRIMARY KEY (appointment_id),
    UNIQUE (appointment_code),
    CONSTRAINT fk_appointment_schedule
                FOREIGN KEY (schedule_id)
                    REFERENCES schedule (schedule_id),
    CONSTRAINT fk_appointment_patient
                    FOREIGN KEY (patient_id)
                        REFERENCES patient (patient_id)
);