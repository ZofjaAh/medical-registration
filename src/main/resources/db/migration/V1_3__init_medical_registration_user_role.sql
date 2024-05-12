CREATE TABLE medical_registration_user_role
(
    user_id     INT     NOT NULL,
    role_id     INT     NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_medical_registration_user_role_user
        FOREIGN KEY (user_id)
            REFERENCES medical_registration_user (user_id),
    CONSTRAINT fk_medical_registration_user_role_role
        FOREIGN KEY (role_id)
            REFERENCES medical_registration_role (role_id)
);