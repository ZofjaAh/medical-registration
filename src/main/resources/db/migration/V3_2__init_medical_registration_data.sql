insert into ADDRESS (address_id, country, city, postal_code, street_address)
values
(1, 'Poland', 'Warsaw', '23-876', 'Zawalna 36/9'),
(2, 'Poland', 'Gdansk', '78-389', 'Ordyna 125/95'),
(3, 'Poland', 'Warsaw', '26-287', 'Senkiewicza 45/19'),
(4, 'Poland', 'Poznan', '62-365', 'Zamkowa 31/73');

SELECT SETVAL('address_address_id_seq', 4);

insert into SPECIALITY (speciality_id, speciality_code, definition)
values
(1, '27-4673', 'pediatria'),
(2, '34-2674', 'medycyna rodzinna');

SELECT SETVAL('speciality_speciality_id_seq', 2);

insert into DOCTOR (doctor_id, doctor_code, name, surname, pesel, email, address_id, speciality_id, user_id)
values
(1, '76015b28-f5f5-4b6d-9da9-6141d61881b5', 'Jakub', 'Pediatra', '93040987864', 'jakub_pediatra@lekaz.pl', 1, 1, 1),
(2, '46lo68fj-fj6k-d37s-a38f-sl81n3h4sw8k', 'Veronika', 'Klinyczna', '85081372834', 'veronika_klinyczna@lekaz.pl', 2, 2, 2);


SELECT SETVAL('doctor_doctor_id_seq', 2);

insert into PATIENT (patient_id, name, surname, pesel, gender, age, phone, email, address_id, user_id)
values
(1, 'Robert', 'Wolny', '52070997836', 'man', 72, '+48 504 203 260', 'robert_wolny@pacjent.pl', 3, 5),
(2, 'Michal', 'Zapalny', '83011863727', 'man', 41, '+48 985 452 864', 'michal_zapalny@pacjent.pl', 4, 6);

SELECT SETVAL('patient_patient_id_seq', 2);

insert into SCHEDULE (schedule_id, schedule_code, date_time, duration, availability, doctor_id)
values
(1, 'jk47d9y3-ki39-sk47-l2h7-jus74n59a7j3', '2024-06-01 10:00:00 UTC', 15, true, 1),
(2, '89e98rju-dj48-sk47-l2h7-jus74n59a7j3', '2024-06-01 10:15:00 UTC', 15, true, 1),
(3, 'ki58s0wk-s9j3-sk47-l2h7-jus74n59a7j3', '2024-07-01 10:00:00 UTC', 20, true, 2),
(4, '98ejk39k-sk4l-sk47-l2h7-ao9ik49fkd0s', '2023-10-01 10:00:00 UTC', 20, false, 2),
(5, '36dh7sjx-sk4l-sk47-l2h7-ao9ik49fkd0s', '2024-11-01 10:00:00 UTC', 20, false, 1),
(6, '92wa5n3k-sk4l-sk47-l2h7-ao9ik49fkd0s', '2024-04-01 12:00:00 UTC', 20, false, 1);

SELECT SETVAL('schedule_schedule_id_seq', 6);

insert into APPOINTMENT (appointment_id, appointment_code, comment_patient, execution, schedule_id, patient_id)
values
(1, 'fj58sk4u-sl69-al4l-l2h7-jus74n59a7j3', 'comment existing patient', true, 4, 1),
(2, 'djk37so8-sl69-al4l-l2h7-jus74n59a7j3', 'comment2 existing patient', false, 5, 1),
(3, 'lo7km6l9-sl69-al4l-l2h7-jus74n59a7j3', 'comment3 existing patient', true, 6, 2);

SELECT SETVAL('appointment_appointment_id_seq', 3);

insert into MEDICAL_RECORD (medical_record_id, medical_record_code, comment_doctor, diagnosis, date_time, appointment_id)
values
(1, 'kj9l3lk4-kl4k-al5l-f2y7-kl3mn77bg3sm', 'comment existing doctor', 'some doctor diagnosis', '2023-10-01 13:00:00 UTC', 1);


SELECT SETVAL('medical_record_medical_record_id_seq', 1);