package moj.project.domain;

import lombok.*;

import java.time.OffsetDateTime;

@Value
@With
@Builder
@EqualsAndHashCode(of = "scheduleCode")
@ToString(of = {"scheduleId", "scheduleCode", "dateTime", "duration", "availability"})
public class Schedule {
    Integer scheduleId;
    String scheduleCode;
    OffsetDateTime dateTime;
    Integer duration;
    Boolean availability;
    Appointment appointment;
    Doctor doctor;

}
