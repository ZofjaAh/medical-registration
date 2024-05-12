package moj.project.util;

import lombok.experimental.UtilityClass;
import moj.project.api.dto.*;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@UtilityClass
public class DtoFixtures {

    public static AppointmentInformationDTO someAppointmentInformation1() {
        return AppointmentInformationDTO.builder()
                .appointmentCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                .patientName("Jakub")
                .patientSurname("Chory")
                .patientPesel("98012132434")
                .patientEmail("jakub_chory@gmail.com")
                .patientGender("man")
                .patientAge(26)
                .commentPatient("some comment")
                .execution(true)
                .build();
    }

    public static AppointmentInformationDTO someAppointmentInformation1_1() {
        return AppointmentInformationDTO.builder()
                .appointmentCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                .patientName("Jakub")
                .patientSurname("Chory")
                .patientPesel("98012132434")
                .patientEmail("jakub_chory@gmail.com")
                .patientGender("man")
                .patientAge(26)
                .commentPatient("some comment")
                .execution(true)
                .scheduleDateTime(OffsetDateTime.of(2024, 5, 17, 13, 00, 00, 00, ZoneOffset.UTC))
                .medicalRecordCode("389jsk48-djk4-kr78-al1j-aj2eu847dk87")
                .commentDoctor("some Doctor comment")
                .diagnosis("medical diagnosis")
                .build();
    }
    public static AppointmentInformationDTO someAppointmentInformation0_1() {
        return AppointmentInformationDTO.builder()
                .patientName("Oleg")
                .patientSurname("Surowy")
                .patientPesel("84122436789")
                .patientEmail("oleg_surowy@pacjent.pl")
                .patientGender("man")
                .patientAge(40)
                .commentPatient("comment Patient")
                .execution(false)
                .scheduleDateTime(OffsetDateTime.of(2024, 6, 1, 10, 00, 00, 00, ZoneOffset.UTC))
                .doctorNameSurname("Jakub Pediatra")
                .build();
    }
    public static AppointmentInformationDTO someAppointmentInformation0_2() {
        return AppointmentInformationDTO.builder()
                .appointmentCode("fj58sk4u-sl69-al4l-l2h7-jus74n59a7j3")
                .patientName("Robert")
                .patientSurname("Wolny")
                .patientPesel("52070997836")
                .patientEmail("robert_wolny@pacjent.pl")
                .patientGender("man")
                .patientAge(72)
                .commentPatient("comment existing patient")
                .execution(true)
                .scheduleDateTime(OffsetDateTime.of(2023, 10, 1, 10, 00, 00, 00, ZoneOffset.UTC))
                .commentDoctor("some Doctor comment")
                .diagnosis("medical diagnosis")
                .build();
    }
    public static AppointmentInformationDTO someAppointmentInformation0_3() {
        return AppointmentInformationDTO.builder()
                .appointmentCode("lo7km6l9-sl69-al4l-l2h7-jus74n59a7j3")
                .patientName("Michal")
                .patientSurname("Zapalny")
                .patientPesel("83011863727")
                .patientGender("man")
                .patientAge(41)
                .patientEmail("michal_zapalny@pacjent.pl")
                .commentPatient("comment3 existing patient")
                .execution(true)
                .scheduleDateTime(OffsetDateTime.of(2024, 4, 1, 12, 00, 00, 00, ZoneOffset.UTC))
                .commentDoctor("some Doctor comment")
                .doctorNameSurname("Jakub Pediatra")
                .diagnosis("medical diagnosis")
                .build();
    }

    public static ScheduleDTO someScheduleTimeSlot1() {
        return ScheduleDTO.builder()
                .date("2025-01-23")
                .time("12:10:00")
                .duration(15)
                .build();
    }
    public static ScheduleDTO someScheduleTimeSlot1_1() {
        return ScheduleDTO.builder()
                .scheduleId(1)
                .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                .dateTime("2025-01-23 12:10:00")
                .duration(15)
                .availability(true)
                .build();
    }

    public static ScheduleDTO someScheduleTimeSlot2() {
        return ScheduleDTO.builder()
                .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                .date("2025-01-23")
                .time("13:00:00")
                .duration(20)
                .build();
    }
    public static ScheduleDTO someScheduleTimeSlot2_1() {
        return ScheduleDTO.builder()
                .scheduleId(1)
                .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                .dateTime("2025-01-23 13:00:00")
                .duration(20)
                .availability(true)
                .build();
    }
    public static ScheduleDTO someScheduleTimeSlot0_1() {
        return ScheduleDTO.builder()
                .scheduleId(1)
                .scheduleCode("jk47d9y3-ki39-sk47-l2h7-jus74n59a7j3")
                .dateTime("2024-06-01 10:00:00")
                .duration(15)
                .availability(true)
                .build();
    }
    public static ScheduleDTO someUpdatedScheduleTimeSlot0_1() {
        return ScheduleDTO.builder()
                .scheduleCode("jk47d9y3-ki39-sk47-l2h7-jus74n59a7j3")
                .date("2024-06-02")
                .time("10:00:00")
                .duration(20)
                .build();
    }
    public static ScheduleDTO someUpdatedScheduleTimeSlot0_2() {
        return ScheduleDTO.builder()
                .scheduleId(1)
                .scheduleCode("jk47d9y3-ki39-sk47-l2h7-jus74n59a7j3")
                .dateTime("2024-06-02 10:00:00")
                .duration(20)
                .availability(true)
                .build();
    }
    public static ScheduleDTO someScheduleTimeSlot0_2() {
        return ScheduleDTO.builder()
                .scheduleId(2)
                .scheduleCode("89e98rju-dj48-sk47-l2h7-jus74n59a7j3")
                .dateTime("2024-06-01 10:15:00")
                .duration(15)
                .availability(true)
                .build();
    }



    public static DoctorScheduleDTO.ScheduleDTO someScheduleTimeSlotDoctorSchedule0_1() {
        return DoctorScheduleDTO.ScheduleDTO.builder()
                 .scheduleCode("ki58s0wk-s9j3-sk47-l2h7-jus74n59a7j3")
                 .dateTime("2024-07-01 10:00:00")
                 .duration(20)
                 .availability(true)
                .build();
    }
    public static DoctorScheduleDTO.ScheduleDTO someScheduleTimeSlotDoctorSchedule0_2() {
        return DoctorScheduleDTO.ScheduleDTO.builder()
                 .scheduleCode("98ejk39k-sk4l-sk47-l2h7-ao9ik49fkd0s")
                 .dateTime("2023-10-01 10:00:00")
                 .duration(20)
                 .availability(false)
                .appointmentCode("fj58sk4u-sl69-al4l-l2h7-jus74n59a7j3")
                .execution(true)
                .medicalRecordCode("kj9l3lk4-kl4k-al5l-f2y7-kl3mn77bg3sm")
                .build();
    }





    public static DoctorDTO someDoctor1() {
        return DoctorDTO.builder()
                .existingDoctorEmail("jakub_pedyatra@lekaz.pl")
                .doctorCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                .name("Jakub")
                .surname("Pedyatra")
                .pesel("93040987864")
                .build();
    }

    public static DoctorDTO someDoctor2() {
        return DoctorDTO.builder()
                .doctorCode("i8j5hs90-j593-547d-djg4-lk84db7987k6")
                .name("Borys")
                .surname("Zawalnyj")
                .pesel("88092136498")
                .specialityCode("1122334455")
                .specialityDefinition("kardyolog")
                .addressCountry("Poland")
                .addressCity("Warsaw")
                .addressPostalCode("24-732")
                .addressStreet("Zamkowa 45/82")
                .build();
    }
    public static DoctorDTO someDoctor0_1() {
        return DoctorDTO.builder()
                .name("Tomasz")
                .surname("Leczniczy")
                .pesel("88092136498")
                .email("tomasz_leczniczy@lekaz.pl")
                .specialityCode("18-3672")
                .specialityDefinition("kardiologia")
                .addressCountry("Poland")
                .addressCity("Warsaw")
                .addressPostalCode("24-732")
                .addressStreet("Zamkowa 45/82")
                .build();
    }
    public static DoctorDTO someDoctor0_2() {
        return DoctorDTO.builder()
                .existingDoctorEmail("jakub_pediatra@lekaz.pl")
                .doctorCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                .name("Jakub")
                .surname("Pediatra")
                .pesel("93040987864")
                .specialityCode("27-4673")
                .specialityDefinition("pediatria")
                .addressCountry("Poland")
                .addressCity("Warsaw")
                .addressPostalCode("23-876")
                .addressStreet("Zawalna 36/9")
                .build();
    }
    public static DoctorDTO someDoctor0_3() {
        return DoctorDTO.builder()
                .existingDoctorEmail("veronika_klinyczna@lekaz.pl")
                .doctorCode("46lo68fj-fj6k-d37s-a38f-sl81n3h4sw8k")
                .name("Veronika")
                .surname("Klinyczna")
                .pesel("85081372834")
                .specialityCode("34-2674")
                .specialityDefinition("medycyna rodzinna")
                .addressCountry("Poland")
                .addressCity("Gdansk")
                .addressPostalCode("78-389")
                .addressStreet("Ordyna 125/95")
                .build();
    }
    public static AppointmentDTO someAppointment0_1() {
        return AppointmentDTO.builder()
                .patientName("Oleg")
                .patientSurname("Surowy")
                .patientPesel("84122436789")
                .patientGender("man")
                .patientAge(40)
                .patientPhone("+48 909 765 003")
                .patientEmail("oleg_surowy@pacjent.pl")
                .patientAddressCountry("Poland")
                .patientAddressCity("Warsaw")
                .patientAddressPostalCode("28-134")
                .patientAddressStreet("Rumiankowa 87/3")
                .commentPatient("comment Patient")
                .scheduleCode("jk47d9y3-ki39-sk47-l2h7-jus74n59a7j3")
                .build();
    }
    public static AppointmentDTO someAppointment1() {
        return AppointmentDTO.builder()
                .patientName("Oleg")
                .patientSurname("Surowy")
                .patientPesel("84122436789")
                .patientGender("man")
                .patientAge(40)
                .patientPhone("+48 909 765 003")
                .patientEmail("oleg_surowy@pacjent.pl")
                .patientAddressCountry("Poland")
                .patientAddressCity("Warsaw")
                .patientAddressPostalCode("28-134")
                .patientAddressStreet("Rumiankowa 87/3")
                .commentPatient("comment Patient")
                .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                .build();
    }
    public static AppointmentDTO someAppointment0_2() {
        return AppointmentDTO.builder()
                .commentPatient("comment Patient")
                .execution(false)
                .build();
    }
    public static AppointmentDTO someAppointment0_3() {
        return AppointmentDTO.builder()
                .existingPatientEmail("robert_wolny@pacjent.pl")
                .commentPatient("comment existing Patient")
                .scheduleCode("89e98rju-dj48-sk47-l2h7-jus74n59a7j3")
                .build();
    }


    public static AppointmentDTO someAppointment2() {
        return AppointmentDTO.builder()
                .appointmentCode("657ghs90-jf83-567d-eyu4-45682dj987k6")
                .commentPatient("comment Patient")
                .execution(false)
                .build();

    }

    public static MedicalRecordDTO someMedicalRecord1() {
        return MedicalRecordDTO.builder()
                .commentDoctor("some Doctor comment")
                .diagnosis("medical diagnosis")
                .build();
    }


    public static MedicalRecordDTO someMedicalRecord0_1() {
        return MedicalRecordDTO.builder()
                .commentDoctor("some Doctor comment")
                .diagnosis("medical diagnosis")
                .appointmentCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                .build();
    }

    public static HealthPatientHistoryDTO someHealthPatientHistory1() {
        return HealthPatientHistoryDTO.builder()
                .patientPesel("98012132434")
                .appointments(List.of(HealthPatientHistoryDTO.AppointmentDTO.builder()
                        .appointmentCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                        .scheduleDateTime("2024-03-23 10:00:00")
                        .commentPatient("some comment")
                        .execution(false)
                        .medicalRecordCode("389jsk48-djk4-kr78-al1j-aj2eu847dk87")
                        .doctorNameSurname("Borys Zawalnyj")
                        .doctorCode("i8j5hs90-j593-547d-djg4-lk84db7987k6")
                        .medicalRecordDateTime("2024-03-23 10:12:00")
                        .medicalRecordCommentDoctor("some Doctor comment")
                        .medicalRecordDiagnosis("medical diagnosis")
                        .build()))
                .build();
    }
    public static HealthPatientHistoryDTO.AppointmentDTO someHealthPatientAppointment0_1(){
        return HealthPatientHistoryDTO.AppointmentDTO.builder()
                .appointmentCode("fj58sk4u-sl69-al4l-l2h7-jus74n59a7j3")
                .scheduleDateTime("2023-10-01 10:00:00")
                .commentPatient("comment existing patient")
                .execution(true)
                .medicalRecordCode("kj9l3lk4-kl4k-al5l-f2y7-kl3mn77bg3sm")
                .doctorNameSurname("Veronika Klinyczna")
                .doctorCode("46lo68fj-fj6k-d37s-a38f-sl81n3h4sw8k")
                .medicalRecordDateTime("2023-10-01 13:00:00")
                .medicalRecordCommentDoctor("comment existing doctor")
                .medicalRecordDiagnosis("some doctor diagnosis")

                .build();
    }
    public static HealthPatientHistoryDTO.AppointmentDTO someHealthPatientAppointment0_2(){
        return HealthPatientHistoryDTO.AppointmentDTO.builder()
                .appointmentCode("djk37so8-sl69-al4l-l2h7-jus74n59a7j3")
                .scheduleDateTime("2024-11-01 10:00:00")
                .commentPatient("comment2 existing patient")
                .execution(false)
                .doctorNameSurname("Jakub Pediatra")
                .doctorCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                .build();
    }

    public static DoctorScheduleDTO someDoctorSchedule1() {
        return DoctorScheduleDTO.builder()
                .doctorNameSurname("Borys Zawalnyj")
                .doctorCode("i8j5hs90-j593-547d-djg4-lk84db7987k6")
                .doctorEmail("borys_zawalnyj@gmail.com")
                .build();
    }


    public static DoctorScheduleDTO.ScheduleDTO someDoctorTimeSlot1() {
        return DoctorScheduleDTO.ScheduleDTO.builder()
                .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                .dateTime("2025-01-23 12:10:00")
                .duration(15)
                .availability(false)
                .appointmentCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                .execution(true)
                .medicalRecordCode("389jsk48-djk4-kr78-al1j-aj2eu847dk87")
                .build();
    }

    public static AppointmentsDTO someAppointments1() {
        Clock clock = Clock.fixed(Instant.parse("2024-08-22T10:00:01Z"), ZoneOffset.UTC);
        return AppointmentsDTO.builder()
                .appointments(List.of(
                        someAppointmentInformation1().withScheduleDateTime(OffsetDateTime.now(clock).plusHours(3))
                                .withExecution(false)
                        )).build();
    }

    public static AppointmentsDTO someAppointments2() {
        Clock clock = Clock.fixed(Instant.parse("2024-08-22T10:00:01Z"), ZoneOffset.UTC);
        return AppointmentsDTO.builder()
                .appointments(List.of(
                        someAppointmentInformation1()
                                .withAppointmentCode("dk9fj4nx-f5f5-4b6d-9da9-6141d61881b5").withScheduleDateTime(OffsetDateTime.now(clock).minusHours(10))
                                .withExecution(true),
                        someAppointmentInformation1()
                                .withAppointmentCode("98ske87f-f5f5-4b6d-9da9-6141d61881b5").withScheduleDateTime(OffsetDateTime.now(clock).minusHours(6))
                                .withExecution(false)
                )).build();
    }

    public static AppointmentsDTO someAppointments3() {
        return AppointmentsDTO.builder()
                .appointments(List.of(
                        someAppointmentInformation1(),
                        someAppointmentInformation1(),
                        someAppointmentInformation1()
                )).build();
    }

    public static DoctorSchedulesDTO someDoctorSchedules1() {
        return DoctorSchedulesDTO.builder()
                .schedules(List.of(
                        someDoctorTimeSlot1(),
                        someDoctorTimeSlot1(),
                        someDoctorTimeSlot1()))
                .build();
    }

    public static DoctorsDTO someDoctors1() {
        return DoctorsDTO.builder()
                .doctors(List.of(someDoctor1(),
                        someDoctor2()))
                .build();
    }

    public static SchedulesDTO someSchedules1() {
        return SchedulesDTO.builder()
                .schedules(List.of(someScheduleTimeSlot1(),
                        someScheduleTimeSlot2()))
                .build();
    }
}
