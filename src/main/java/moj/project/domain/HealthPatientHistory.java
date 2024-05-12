package moj.project.domain;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.util.List;

@Value
@With
@Builder
@ToString(of = "patientPesel")
public class HealthPatientHistory {
    String patientPesel;
    List<Appointment> appointments;

    @Value
    @With
    @Builder
    @ToString(of = {"appointmentCode",  "commentPatient"})
    public static class Appointment {

        String appointmentCode;
        String commentPatient;
        Boolean execution;
        Schedule schedule;
        MedicalRecord medicalRecord;

    }
}
