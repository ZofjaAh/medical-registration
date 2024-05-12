package moj.project.api.dto;

import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthPatientHistoryDTO {
    private String patientPesel;
    private List<AppointmentDTO> appointments;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppointmentDTO {
        private String appointmentCode;
        private String scheduleDateTime;
        private String commentPatient;
        private Boolean execution;
        private String medicalRecordCode;
        private String doctorNameSurname;
        private String doctorCode;
        private String medicalRecordDateTime;
        private String medicalRecordCommentDoctor;
        private String medicalRecordDiagnosis;



    }

}
