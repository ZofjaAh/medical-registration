package moj.project.api.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDTO {
    private Integer scheduleId;
    private String scheduleCode;
    private String dateTime;
    private String date;
    private String time;
    private Integer duration;
    private Boolean availability;
    private String doctorCode;
    public Map<String, String> asMap(){
        Map<String, String> result = new HashMap<>();
        Optional.ofNullable(scheduleId).ifPresent(value-> result.put("scheduleId", value.toString()));
        Optional.ofNullable(scheduleCode).ifPresent(value-> result.put("scheduleCode", value));
        Optional.ofNullable(dateTime).ifPresent(value-> result.put("dateTime", value));
        Optional.ofNullable(date).ifPresent(value-> result.put("date", value));
        Optional.ofNullable(time).ifPresent(value-> result.put("time", value));
        Optional.ofNullable(duration).ifPresent(value-> result.put("duration", value.toString()));
        Optional.ofNullable(availability).ifPresent(value-> result.put("availability", value.toString()));
        Optional.ofNullable(doctorCode).ifPresent(value-> result.put("doctorCode", value));
        return result;
    }


}
