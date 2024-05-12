insert into MEDICAL_REGISTRATION_ROLE (role_id, role) values (1, 'DOCTOR'), (2, 'PATIENT'), (3, 'REST_API');

insert into MEDICAL_REGISTRATION_USER (user_id, user_name, email, password, active) values (1, 'jakub_pediatra', 'jakub_pediatra@lekaz.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into MEDICAL_REGISTRATION_USER (user_id, user_name, email, password, active) values (2, 'veronika_klinyczna', 'veronika_klinyczna@lekaz.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into MEDICAL_REGISTRATION_USER (user_id, user_name, email, password, active) values (3, 'tomasz_leczniczy', 'tomasz_leczniczy@lekaz.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into MEDICAL_REGISTRATION_USER (user_id, user_name, email, password, active) values (4, 'jozef_dentysta', 'jozef_dentysta@lekaz.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into MEDICAL_REGISTRATION_USER (user_id, user_name, email, password, active) values (5, 'robert_wolny', 'robert_wolny@pacjent.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into MEDICAL_REGISTRATION_USER (user_id, user_name, email, password, active) values (6, 'michal_zapalny', 'michal_zapalny@pacjent.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into MEDICAL_REGISTRATION_USER (user_id, user_name, email, password, active) values (7, 'anneta_zabiegova', 'anneta_zabiegova@pacjent.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);
insert into MEDICAL_REGISTRATION_USER (user_id, user_name, email, password, active) values (8, 'oleg_surowy', 'oleg_surowy@pacjent.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

insert into MEDICAL_REGISTRATION_USER (user_id, user_name, email, password, active) values (9, 'test_user', 'test_user@rest.pl', '$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G', true);

SELECT SETVAL('medical_registration_user_user_id_seq', 9);

insert into MEDICAL_REGISTRATION_USER_ROLE (user_id, role_id) values (1, 1), (2, 1), (3, 1), (4, 1);
insert into MEDICAL_REGISTRATION_USER_ROLE (user_id, role_id) values (5, 2), (6, 2), (7, 2), (8, 2);
insert into MEDICAL_REGISTRATION_USER_ROLE (user_id, role_id) values (9, 3);

