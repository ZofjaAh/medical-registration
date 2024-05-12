package moj.project.api.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentInformationDTO {
    private String appointmentCode;
    private String patientName;
    private String patientSurname;
    private String patientPesel;
    private String patientEmail;
    private String patientGender;
    private Integer patientAge;
    private String commentPatient;
    private Boolean execution;
    private OffsetDateTime scheduleDateTime;
    private String medicalRecordCode;
    private String doctorNameSurname;
    private String commentDoctor;
    private String diagnosis;

}
