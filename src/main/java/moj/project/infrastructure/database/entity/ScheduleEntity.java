package moj.project.infrastructure.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@With
@Setter
@EqualsAndHashCode(of = "scheduleId")
@ToString(of = {"scheduleId", "scheduleCode", "dateTime", "duration", "availability"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schedule")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "scheduleId")
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Integer scheduleId;

    @Column(name = "schedule_code")
    private String scheduleCode;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "availability")
    private Boolean availability;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "schedule")
    private AppointmentEntity appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private DoctorEntity doctor;

}
