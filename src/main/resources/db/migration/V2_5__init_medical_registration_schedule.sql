CREATE TABLE schedule
(
    schedule_id    SERIAL                      NOT NULL,
    schedule_code  VARCHAR(64)                 NOT NULL,
    date_time      TIMESTAMP WITH TIME ZONE    NOT NULL,
    duration       INT                         NOT NULL,
    availability   BOOLEAN                     NOT NULL,
    doctor_id      INT                         NOT NULL,
    PRIMARY KEY (schedule_id),
    UNIQUE (schedule_code),
    UNIQUE (date_time, doctor_id),
    CONSTRAINT fk_schedule_doctor
                FOREIGN KEY (doctor_id)
                    REFERENCES doctor (doctor_id)
);