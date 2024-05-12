package moj.project.domain;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import lombok.With;

import java.time.OffsetDateTime;
import java.util.List;

@Value
@With
@Builder
@ToString(of={"doctorNameSurname", "doctorCode"})
public class DoctorSchedule {
    String doctorNameSurname;
    String doctorCode;
    String doctorEmail;
    List<Schedule> schedules;


    @Value
    @With
    @Builder
    @ToString(of={"scheduleCode", "dateTime", "duration", "availability"})
    public static class Schedule {
        String scheduleCode;
        OffsetDateTime dateTime;
        Integer duration;
        Boolean availability;
        Appointment appointment;
        MedicalRecord medicalRecord;
    }
}






