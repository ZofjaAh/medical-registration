package moj.project.domain;

import lombok.*;

import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "doctorId")
@ToString(of = {"doctorId", "name", "surname", "pesel", "email"})
public class Doctor {
    Integer doctorId;
    String doctorCode;
    String name;
    String surname;
    String pesel;
    String email;
    Speciality speciality;
    Address address;
    Set<Schedule> schedules;


}
