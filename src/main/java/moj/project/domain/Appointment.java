package moj.project.domain;

import lombok.*;

@Value
@With
@Builder
@EqualsAndHashCode(of = "appointmentId")
@ToString(of = {"appointmentId", "appointmentCode", "commentPatient", "execution" })
public class Appointment {
    Integer appointmentId;
    String appointmentCode;
    String commentPatient;
    Boolean execution;
    MedicalRecord medicalRecord;
    Schedule schedule;
    Patient patient;

}
