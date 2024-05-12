package moj.project.util;

import lombok.experimental.UtilityClass;
import moj.project.domain.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@UtilityClass
public class DomainFixtures {
    public static Doctor someDoctor1() {
        return Doctor.builder()
                .name("Borys")
                .surname("Zawalnyj")
                .pesel("88092136498")
                .email("borys_zawalny88@gmail.pl")
                .speciality(Speciality.builder()
                        .specialityCode("1122334455")
                        .definition("kardyolog")
                        .build())
                .address(Address.builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("24-732")
                        .streetAddress("Zamkowa 45/82")
                        .build())
                .build();
    }

    public static Appointment someAppointment1() {
        return Appointment.builder()
                .appointmentCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                .commentPatient("some comment")
                .execution(true)
                .build();
    }

    public static Appointment someAppointment1_1() {
        return Appointment.builder()
                .appointmentId(1)
                .appointmentCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                .commentPatient("some comment")
                .execution(true)
                .medicalRecord(someMedicalRecord1())
                .patient(Patient.builder()
                        .name("Jakub")
                        .surname("Chory")
                        .pesel("98012132434")
                        .gender("man")
                        .age(26)
                        .email("jakub_chory@gmail.com")
                        .build())
                .schedule(Schedule.builder()
                        .dateTime(OffsetDateTime.of(2024, 5, 17, 13, 00, 00, 00, ZoneOffset.UTC))
                        .build())

                .build();

    }

    public static MedicalRecord someMedicalRecord1() {
        return MedicalRecord.builder()
                .medicalRecordCode("389jsk48-djk4-kr78-al1j-aj2eu847dk87")
                .commentDoctor("some Doctor comment")
                .diagnosis("medical diagnosis")
                .build();
    }

    public static Appointment someAppointment2() {
        return Appointment.builder()
                .appointmentId(1)
                .appointmentCode("657ghs90-jf83-567d-eyu4-45682dj987k6")
                .commentPatient("comment Patient")
                .execution(false)
                .schedule(Schedule.builder()
                        .scheduleId(1)
                        .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                        .dateTime(OffsetDateTime.of(2024, 3, 23, 10, 00, 00, 00, ZoneOffset.UTC))
                        .duration(15)
                        .availability(false)
                        .build())

                .build();
    }

    public static AppointmentRequest someAppointmentRequest1() {
        return AppointmentRequest.builder()
                .patientName("Oleg")
                .patientSurname("Surowy")
                .patientPesel("84122436789")
                .patientGender("man")
                .patientAge(40)
                .patientPhone("+48 909 765 003")
                .patientEmail("oleg_surowy@gmail.pl")
                .patientAddressCountry("Poland")
                .patientAddressCity("Warsaw")
                .patientAddressPostalCode("28-134")
                .patientAddressStreet("Rumiankowa 87/3")
                .commentPatient("comment Patient")
                .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                .build();
    }

    public static HealthPatientHistory someHealthPatientHistory1() {
        return HealthPatientHistory.builder()
                .patientPesel("98012132434")
                .appointments(List.of(HealthPatientHistory.Appointment.builder()
                        .appointmentCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
                        .commentPatient("some comment")
                       .execution(false)
                        .schedule(Schedule.builder()
                                .dateTime(OffsetDateTime.of(2024, 3, 23, 10, 00, 00, 00, ZoneOffset.UTC))
                                .doctor(Doctor.builder()
                                        .name("Borys")
                                        .surname("Zawalnyj")
                                        .doctorCode("i8j5hs90-j593-547d-djg4-lk84db7987k6")
                                        .build())
                                .build())
                        .medicalRecord(MedicalRecord.builder()
                                .medicalRecordCode("389jsk48-djk4-kr78-al1j-aj2eu847dk87")
                                .dateTime(OffsetDateTime.of(2024, 3, 23, 10, 12, 00, 00, ZoneOffset.UTC))
                                .commentDoctor("some Doctor comment")
                                .diagnosis("medical diagnosis")


                                .build())
                        .build()
                ))
                .build();

    }

    public static DoctorSchedule someDoctorSchedule1() {
        return DoctorSchedule.builder()
                .doctorNameSurname("Borys Zawalnyj")
                .doctorCode("i8j5hs90-j593-547d-djg4-lk84db7987k6")
                .doctorEmail("borys_zawalnyj@gmail.com")
                .build();
    }

    public static Schedule someSchedule1() {
        return Schedule.builder()
                .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2025,1,23,12,10,00,00,ZoneOffset.UTC))
                .duration(15)
                .availability(true)
                .build();
    }
    public static Schedule someSchedule1_1() {
        return Schedule.builder()
                .scheduleId(1)
                .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2025,1,23,12,10,00,00,ZoneOffset.UTC))
                .duration(15)
                .availability(true)
                .build();
    }
    public static Schedule someSchedule1_2() {
        return Schedule.builder()
                .scheduleId(1)
                .scheduleCode("7aa673af-b77e-4e2c-92e7-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2025,1,23,13,00,00,00,ZoneOffset.UTC))
                .duration(20)
                .availability(true)
                .build();
    }


    public static Schedule someSchedule2() {
        return Schedule.builder()
                        .scheduleId(2)
                        .scheduleCode("lo09f56af-ui77e-4j0c-02e7-43fa5fce1961")
                        .dateTime(OffsetDateTime.of(2024,10,6,11,00,00,00,ZoneOffset.UTC))
                        .duration(20)
                        .availability(true)
                        .build();
    }


    public static Doctor someDoctor2() {
        return Doctor.builder()
                .doctorId(2)
                .doctorCode("jk58s9af-587e-4e2c-92e7-8dh54fce1961")
                .name("Georg")
                .surname("Leczacy")
                .pesel("72120223456")
                .email("georg_leczacy72@gmail.ru")
                .build();
    }
}
