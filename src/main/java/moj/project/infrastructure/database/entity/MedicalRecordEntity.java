package moj.project.infrastructure.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = "medicalRecordId")
@ToString(of = {"medicalRecordId", "medicalRecordCode", "commentDoctor", "diagnosis", "dateTime"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medical_record")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "medicalRecordId")
public class MedicalRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_record_id")
    private Integer medicalRecordId;

    @Column(name = "medical_record_code")
    private String medicalRecordCode;

    @Column(name = "comment_doctor")
    private String commentDoctor;

    @Column(name = "diagnosis")
    private String diagnosis;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;
}
