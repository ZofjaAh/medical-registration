package moj.project.util;

import lombok.experimental.UtilityClass;
import moj.project.infrastructure.database.entity.*;
import moj.project.infrastructure.security.RoleEntity;
import moj.project.infrastructure.security.UserEntity;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@UtilityClass
public class EntityFixtures {
    public static UserEntity someUser1() {
        return UserEntity.builder()
                .userName("roman_niespiency")
                .email("roman_niespiency@pacjent.pl")
                .password("$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G")
                .active(true)
                .roles(Set.of(RoleEntity.builder()
                                .id(2)
                        .role("PATIENT").build()))
                .build();
      }
      public static MedicalRecordEntity someMedicalRecord1(){
        return MedicalRecordEntity.builder()
          .medicalRecordCode("eu68skw9-28fk-s0l4-sl2g-cj58s62f47sk")
          .commentDoctor("some doctor comment 1")
          .diagnosis("medical diagnosis 1")
          .dateTime(OffsetDateTime.of(2025,1,1,12,10,00,00, ZoneOffset.UTC))
                .appointment(someAppointment0_2()).build();
      }
      public static MedicalRecordEntity someMedicalRecord0_1(){
        return MedicalRecordEntity.builder()
                .medicalRecordId(1)
          .medicalRecordCode("kj9l3lk4-kl4k-al5l-f2y7-kl3mn77bg3sm")
          .commentDoctor("comment existing doctor")
          .diagnosis("some doctor diagnosis")
          .dateTime(OffsetDateTime.of(2023,10,1,13,10,00,00, ZoneOffset.UTC))
                .build();
      }
      public static MedicalRecordEntity someMedicalRecord3(){
        return MedicalRecordEntity.builder()
          .medicalRecordCode("l9f7h3a8-28fk-s0l4-sl2g-cj58s62f47sk")
          .commentDoctor("some doctor comment 3")
          .diagnosis("medical diagnosis 3")
          .dateTime(OffsetDateTime.of(2025,3,3,12,10,00,00, ZoneOffset.UTC))
                .build();
      }


    public static ScheduleEntity someSchedule1() {
        return ScheduleEntity.builder()
                .scheduleCode("lo09f56af-dk48-sk35-02e7-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2024,1,1,11,00,00,00,ZoneOffset.UTC))
                .duration(20)
                .availability(true)
                .doctor(someDoctor0_2())
                .build();
    }
    public static ScheduleEntity someSchedule1_1() {
        return ScheduleEntity.builder()
                .scheduleCode("ki9dj42uy-0c9d-k3mn-dk2n-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2024,1,1,11,20,00,00,ZoneOffset.UTC))
                .duration(20)
                .availability(true)
                .doctor(someDoctor0_2())
                .build();
    }
    public static ScheduleEntity someSchedule1_2() {
        return ScheduleEntity.builder()
                .scheduleCode("lo09f56af-dk48-sk35-02e7-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2024,1,1,11,00,00,00,ZoneOffset.UTC))
                .duration(20)
                .availability(false)
                .doctor(someDoctor1())
                .build();
    }
    public static ScheduleEntity someSchedule0_1() {
        return ScheduleEntity.builder()
                .scheduleId(2)
                .scheduleCode("89e98rju-dj48-sk47-l2h7-jus74n59a7j3")
                .dateTime(OffsetDateTime.of(2024, 6, 1, 10, 15, 00, 00, ZoneOffset.UTC))
                .duration(15)
                .availability(false)
                .doctor(someDoctor0_1())
                .build();
    }

    public static ScheduleEntity someSchedule0_2() {
        return ScheduleEntity.builder()
                .scheduleId(1)
                .scheduleCode("jk47d9y3-ki39-sk47-l2h7-jus74n59a7j3")
                .dateTime(OffsetDateTime.of(2024, 6, 1, 10, 00, 00, 00, ZoneOffset.UTC))
                .duration(15)
                .availability(false)
                .doctor(someDoctor0_1())
                .build();
    }
    public static ScheduleEntity someSchedule0_3() {
        return ScheduleEntity.builder()
                .scheduleId(1)
                .scheduleCode("ki58s0wk-s9j3-sk47-l2h7-jus74n59a7j3")
                .dateTime(OffsetDateTime.of(2024, 7, 1, 10, 00, 00, 00, ZoneOffset.UTC))
                .duration(20)
                .availability(false)
                .doctor(someDoctor0_2())
                .build();
    }
    public static ScheduleEntity someSchedule0_4() {
        return ScheduleEntity.builder()
                .scheduleId(4)
                .scheduleCode("98ejk39k-sk4l-sk47-l2h7-ao9ik49fkd0s")
                .dateTime(OffsetDateTime.of(2023, 10, 1, 10, 00, 00, 00, ZoneOffset.UTC))
                .duration(20)
                .availability(false)
                .doctor(someDoctor0_2())
                .build();
    }
        public static ScheduleEntity someSchedule2_2() {
        return ScheduleEntity.builder()
                .scheduleCode("p05id49s-ui77e-4j0c-02e7-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2024,2,2,10,00,00,00,ZoneOffset.UTC))
                .duration(20)
                .availability(true)
                .doctor(someDoctor2())
                .build();

    } public static ScheduleEntity someSchedule2() {
        return ScheduleEntity.builder()
                .scheduleCode("p05id49s-ui77e-4j0c-02e7-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2024,2,2,10,00,00,00,ZoneOffset.UTC))
                .duration(20)
                .availability(true)
                .doctor(someDoctor2())
                .build();

    }

    public static ScheduleEntity someSchedule3() {
        return  ScheduleEntity.builder()
                .scheduleCode("ud7w835k-28sk-4j0c-02e7-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2024,3,3,11,00,00,00,ZoneOffset.UTC))
                .duration(15)
                .availability(false)
                .build();
    }
    public static ScheduleEntity someSchedule3_2() {
        return  ScheduleEntity.builder()
                .scheduleCode("ud7w835k-28sk-4j0c-02e7-43fa5fce1961")
                .dateTime(OffsetDateTime.of(2024,3,3,11,00,00,00,ZoneOffset.UTC))
                .duration(15)
                .availability(false)
                .doctor(someDoctor3())
                .build();
    }


    public static DoctorEntity someDoctor1() {
        return DoctorEntity.builder()
                .doctorCode("i8j5hs90-j593-547d-djg4-lk84db7987k6")
        .name("Tomasz")
        .surname("Leczniczy")
        .pesel("88092136498")
        .email("tomasz_leczniczy@lekaz.pl")
                .address(someAddress1())
                .speciality(someSpeciality1())
                .user(someUser0_3())
       .build();
    }
    public static DoctorEntity someDoctor0_1() {
        return DoctorEntity.builder()
                .doctorId(1)
                .doctorCode("76015b28-f5f5-4b6d-9da9-6141d61881b5")
        .name("Jakub")
        .surname("Pediatra")
        .pesel("93040987864")
        .email("jakub_pediatra@lekaz.pl")
                .address(someAddress0_1())
                .speciality(someSpeciality0_1())
       .build();
    }
    public static DoctorEntity someDoctor0_2() {
        return DoctorEntity.builder()
                .doctorId(2)
                .doctorCode("46lo68fj-fj6k-d37s-a38f-sl81n3h4sw8k")
        .name("Veronika")
        .surname("Klinyczna")
        .pesel("85081372834")
        .email("veronika_klinyczna@lekaz.pl")
       .build();
    }

    public static DoctorEntity someDoctor2() {
        return DoctorEntity.builder()
                .doctorCode("jk58s9af-587e-4e2c-92e7-8dh54fce1961")
                .name("Georg")
                .surname("Leczacy")
                .pesel("72120223456")
                .email("georg_leczacy72@gmail.ru")
                .build();
    }

    public static AppointmentEntity someAppointment1() {
        return AppointmentEntity.builder()
                .appointmentCode("657ghs90-jf83-567d-eyu4-45682dj987k6")
                .commentPatient("comment Patient 1")
                .execution(false)
                .build();
    }
    public static AppointmentEntity someAppointment1_1() {
        return AppointmentEntity.builder()
                .appointmentCode("657ghs90-jf83-567d-eyu4-45682dj987k6")
                .commentPatient("comment Patient 1")
                .execution(true)
                .medicalRecord(someMedicalRecord1())
                .schedule(someSchedule1_2())
                .build();
    }
    public static AppointmentEntity someAppointment1_2() {
        return AppointmentEntity.builder()
                .appointmentCode("657ghs90-jf83-567d-eyu4-45682dj987k6")
                .commentPatient("comment Patient 1")
                .execution(true)
                .schedule(someSchedule0_1())
                .patient(somePatient0_2())
                .build();
    }

    public static AppointmentEntity someAppointment2() {
        return AppointmentEntity.builder()
                .appointmentCode("io57sj38-er43-er58-w23x-45682dj987k6")
                .commentPatient("comment Patient 2")
                .execution(false)
                .build();

    }
    public static AppointmentEntity someAppointment2_1() {
        return AppointmentEntity.builder()
                .appointmentCode("io57sj38-er43-er58-w23x-45682dj987k6")
                .commentPatient("comment Patient 2")
                .execution(false)
                .schedule(someSchedule2_2())
                .build();

    }
public static AppointmentEntity someAppointment0_1() {
        return AppointmentEntity.builder()
                .appointmentCode("fj58sk4u-sl69-al4l-l2h7-jus74n59a7j3")
                .commentPatient("comment existing patient")
                .execution(true)
                .schedule(someSchedule0_4())
                .patient(somePatient0_2())
                .medicalRecord(someMedicalRecord0_1())
                .build();

    }
    static AppointmentEntity someAppointment0_2() {
        return AppointmentEntity.builder()
                .appointmentId(3)
                .appointmentCode("lo7km6l9-sl69-al4l-l2h7-jus74n59a7j3")
                .commentPatient("comment3 existing patient")
                .execution(true)
                .build();

    }
    public static AppointmentEntity someAppointment3() {
         return AppointmentEntity.builder()
                .appointmentCode("jk7ry57w-er43-er58-w23x-45682dj987k6")
                .commentPatient("comment Patient 2")
                .execution(false)
                .schedule(someSchedule0_1())
                .patient(somePatient0_1())
                .build();

    }
    public static AppointmentEntity someAppointment4() {
         return AppointmentEntity.builder()
                .appointmentCode("657ghs90-jf83-567d-eyu4-45682dj987k6")
                .commentPatient("comment Patient 3")
                .execution(false)
                .schedule(someSchedule0_3())
                .patient(somePatient0_1())
                .build();

    }
    public static AppointmentEntity someAppointment3_1() {
        return AppointmentEntity.builder()
                .appointmentCode("jk7ry57w-er43-er58-w23x-45682dj987k6")
                .commentPatient("comment Patient 3")
                .execution(true)
                .schedule(someSchedule3_2())
                .build();

    }
    public static AppointmentEntity someAppointment3_2() {
        return AppointmentEntity.builder()
                .appointmentCode("jk7ry57w-er43-er58-w23x-45682dj987k6")
                .commentPatient("comment Patient 3")
                .execution(true)
                .schedule(someSchedule3_2())
                .patient(somePatient2())
                .build();

    }

    public static DoctorEntity someDoctor3() {
        return  DoctorEntity.builder()
                .doctorCode("ui36sh57-78so-48sj-2u7w-8dh54fce1961")
                .name("Zachar")
                .surname("Szpitalny")
                .pesel("66091738294")
                .email("zachar_szpitalny@lekaz.pl")
                .build();
    }

    public static PatientEntity somePatient1() {
        return PatientEntity.builder()
                .name("Oleg")
                .surname("Surowy")
                .pesel("98012132434")
                .email("oleg_surowy@pacjent.pl")
                .gender("man")
                .age(26)
                .phone("+48 687 958 875")
                .address(someAddress1())
                .user(someUser0_8())
                .build();
    }
    public static PatientEntity somePatient0_1() {
        return PatientEntity.builder()
                .name("Robert")
                .surname("Wolny")
                .pesel("52070997836")
                .email("robert_wolny@pacjent.pl")
                .gender("man")
                .age(72)
                .phone("+48 504 203 260")
                .build();
    }
    public static PatientEntity somePatient0_2() {
        return PatientEntity.builder()
                .patientId(1)
                .name("Robert")
                .surname("Wolny")
                .pesel("52070997836")
                .email("robert_wolny@pacjent.pl")
                .gender("man")
                .age(72)
                .phone("+48 504 203 260")
                .address(someAddress0_3())
                .user(someUser0_5())
                .build();
    }

    private static PatientEntity somePatient2() {
        return PatientEntity.builder()
                .name("Adam")
                .surname("Slaby")
                .pesel("94060956732")
                .email("adam_slaby@pacjent.pl")
                .gender("man")
                .age(28)
                .address(someAddress2())
                .user(someUser3())
                .build();
    }

    private static UserEntity someUser0_8() {
     return    UserEntity.builder()
             .id(8)
                .userName("oleg_surowy")
                .email("oleg_surowy@pacjent.pl")
                .password("$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G")
                .active(true)
                .roles(Set.of(RoleEntity.builder()
                        .id(2)
                        .role("PATIENT").build()))
                .build();
    }
    private static UserEntity someUser0_5() {
     return    UserEntity.builder()
             .id(5)
                .userName("robert_wolny")
                .email("robert_wolny@pacjent.pl")
                .password("$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G")
                .active(true)
                .roles(Set.of(RoleEntity.builder()
                        .id(2)
                        .role("PATIENT").build()))
                .build();
    }  private static UserEntity someUser0_3() {
     return    UserEntity.builder()
             .id(3)
                .userName("tomasz_leczniczy")
                .email("tomasz_leczniczy@lekaz.pl")
                .password("$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G")
                .active(true)
                .roles(Set.of(RoleEntity.builder()
                        .id(2)
                        .role("PATIENT").build()))
                .build();
    }

    private static UserEntity someUser3() {
        return    UserEntity.builder()
                .userName("adam_slaby")
                .email("adam_slaby@pacjent.pl")
                .password("$2a$12$TwQsp1IusXTDl7LwZqL0qeu49Ypr6vRdEzRq2vAsgb.zvOtrnzm5G")
                .active(true)
                .roles(Set.of(RoleEntity.builder()
                        .id(2)
                        .role("PATIENT").build()))
                .build();
    }

    private static AddressEntity someAddress1() {
        return AddressEntity.builder()
                .country("Poland")
                .city("Warsaw")
                .postalCode("28-134")
                .streetAddress("Rumiankowa 87/3")
                .build();
    }
    private static AddressEntity someAddress0_1() {
        return AddressEntity.builder()
                .addressId(1)
                .country("Poland")
                .city("Warsaw")
                .postalCode("23-876")
                .streetAddress("Zawalna 36/9")
                .build();
    }
    private static AddressEntity someAddress0_3() {
        return AddressEntity.builder()
                .addressId(3)
                .country("Poland")
                .city("Warsaw")
                .postalCode("26-287")
                .streetAddress("Senkiewicza 45/19")
                .build();
    }
    private static AddressEntity someAddress2() {
        return AddressEntity.builder()
                .country("Czechy")
                .city("Praga")
                .postalCode("47-278")
                .streetAddress("Zagorna 56/24")
                .build();
    }
    private static SpecialityEntity someSpeciality0_1() {
        return SpecialityEntity.builder()
                .specialityId(1)
                .specialityCode("27-4673")
                .definition("pediatria")
                .build();
    }
    private static SpecialityEntity someSpeciality1() {
        return SpecialityEntity.builder()
                .specialityCode("45-687")
                .definition("ginekologia")
                .build();
    }


}
