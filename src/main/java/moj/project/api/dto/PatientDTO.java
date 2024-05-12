package moj.project.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {
    private String existingPatientEmail;

    private String patientName;
    private String patientSurname;
    private String patientPesel;
    private String patientGender;
    private String patientAge;
    @Size(min = 7, max = 15)
    @Pattern(regexp = "^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String patientPhone;
    @Email
    private String patientEmail;
    private String patientAddressCountry;
    private String patientAddressCity;
    private String patientAddressPostalCode;
    private String patientAddressStreet;
    
}
