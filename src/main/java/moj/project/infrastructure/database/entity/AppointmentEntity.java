package moj.project.infrastructure.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "appointmentId")
@ToString(of = {"appointmentId", "appointmentCode", "commentPatient", "execution"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "appointmentId")
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer appointmentId;

    @Column(name = "appointment_code")
    private String appointmentCode;

    @Column(name = "comment_patient")
    private String commentPatient;

    @Column(name = "execution")
    private Boolean execution;


    @OneToOne(fetch = FetchType.EAGER, mappedBy = "appointment")
    private MedicalRecordEntity medicalRecord;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private ScheduleEntity schedule;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

}
