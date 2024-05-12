package moj.project.api.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private String existingPatientEmail;
    private String patientName;
    private String patientSurname;
    private String patientPesel;
    private String patientGender;
    private Integer patientAge;
    @Size(min = 7, max = 15)
    @Pattern(regexp = "|^[+]\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}$")
    private String patientPhone;
    @Email
    @Nullable
    private String patientEmail;
    private String patientAddressCountry;
    private String patientAddressCity;
    private String patientAddressPostalCode;
    private String patientAddressStreet;
    private String appointmentCode;
    private String commentPatient;
    private Boolean execution;
    private String scheduleCode;
    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
        ofNullable(existingPatientEmail).ifPresent(value-> result.put("existingPatientEmail", value));
        ofNullable(patientName).ifPresent(value-> result.put("patientName", value));
        ofNullable(patientSurname).ifPresent(value-> result.put("patientSurname", value));
        ofNullable(patientPesel).ifPresent(value-> result.put("patientPesel", value));
        ofNullable(patientGender).ifPresent(value-> result.put("patientGender", value));
        ofNullable(patientAge).ifPresent(value-> result.put("patientAge", value.toString()));
        ofNullable(patientPhone).ifPresent(value-> result.put("patientPhone", value));
        ofNullable(patientEmail).ifPresent(value-> result.put("patientEmail", value));
        ofNullable(patientAddressCountry).ifPresent(value-> result.put("patientAddressCountry", value));
        ofNullable(patientAddressCity).ifPresent(value-> result.put("patientAddressCity", value));
        ofNullable(patientAddressPostalCode).ifPresent(value-> result.put("patientAddressPostalCode", value));
        ofNullable(patientAddressStreet).ifPresent(value-> result.put("patientAddressStreet", value));
        ofNullable(appointmentCode).ifPresent(value-> result.put("appointmentCode", value));
        ofNullable(commentPatient).ifPresent(value-> result.put("commentPatient", value));
        ofNullable(execution).ifPresent(value-> result.put("execution", value.toString()));
        ofNullable(scheduleCode).ifPresent(value-> result.put("scheduleCode", value));
        return  result;
    }
}
