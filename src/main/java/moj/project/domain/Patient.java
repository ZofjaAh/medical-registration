package moj.project.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@With
@Value
@Builder
@EqualsAndHashCode(of = "email")
@ToString(of = {"patientId", "name", "surname", "pesel", "gender", "age", "phone", "email"})
public class Patient {
    Integer patientId;
    String name;
    String surname;
    String pesel;
    String gender;
    Integer age;
    String phone;
    String email;
    Address address;
    Set<Appointment> appointments;
    public Set<Appointment> getAppointments(){

        return Objects.isNull(appointments) ? new HashSet<>() : appointments;
    }

    }
