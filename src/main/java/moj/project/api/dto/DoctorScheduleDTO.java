package moj.project.api.dto;

import lombok.*;

import java.util.List;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorScheduleDTO {
    private String doctorNameSurname;
    private String doctorCode;
    private String doctorEmail;
    private List<ScheduleDTO> schedules;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScheduleDTO {
        private String scheduleCode;
        private String dateTime;
        private Integer duration;
        private Boolean availability;
        private String appointmentCode;
        private Boolean execution;
        private String medicalRecordCode;
    }
}






