package moj.project.domain;

import lombok.*;

import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "addressId")
@ToString(of = {"addressId", "country", "city", "postalCode", "streetAddress"})
public class Address {
    Integer addressId;
    String country;
    String city;
    String postalCode;
    String streetAddress;
    Set<Doctor> doctors;
    Set<Patient> patients;
}
