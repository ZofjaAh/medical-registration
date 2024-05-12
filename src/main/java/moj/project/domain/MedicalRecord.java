package moj.project.domain;

import lombok.*;

import java.time.OffsetDateTime;

@Value
@With
@Builder
@EqualsAndHashCode(of = "medicalRecordId")
@ToString(of = {"medicalRecordId", "medicalRecordCode", "commentDoctor", "diagnosis", "dateTime"})
public class MedicalRecord {
    Integer medicalRecordId;
    String medicalRecordCode;
    String commentDoctor;
    String diagnosis;
    OffsetDateTime dateTime;
    Appointment appointment;
}
