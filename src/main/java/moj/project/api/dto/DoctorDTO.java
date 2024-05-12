package moj.project.api.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {
    private String existingDoctorEmail;
    private String doctorCode;

    private String name;
    private String surname;
    private String pesel;
    @Email
    private String email;
    private String specialityCode;
    private String specialityDefinition;
    private String addressCountry;
    private String addressCity;
    private String addressPostalCode;
    private String addressStreet;

public Map<String, String> asMap(){
    Map<String,String> result = new HashMap<>();
    ofNullable(existingDoctorEmail).ifPresent(value-> result.put("existingDoctorEmail", value));
    ofNullable(doctorCode).ifPresent(value-> result.put("doctorCode", value));
    ofNullable(name).ifPresent(value-> result.put("name", value));
    ofNullable(surname).ifPresent(value-> result.put("surname", value));
    ofNullable(pesel).ifPresent(value-> result.put("pesel", value));
    ofNullable(email).ifPresent(value-> result.put("email", value));
    ofNullable(specialityCode).ifPresent(value-> result.put("specialityCode", value));
    ofNullable(specialityDefinition).ifPresent(value-> result.put("specialityDefinition", value));
    ofNullable(addressCountry).ifPresent(value-> result.put("addressCountry", value));
    ofNullable(addressCity).ifPresent(value-> result.put("addressCity", value));
    ofNullable(addressPostalCode).ifPresent(value-> result.put("addressPostalCode", value));
    ofNullable(addressStreet).ifPresent(value-> result.put("addressStreet", value));
    return result;
}
}
