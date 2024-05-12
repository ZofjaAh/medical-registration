package moj.project.api.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

import static java.util.Optional.ofNullable;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalRecordDTO {
    private String medicalRecordCode;
    private String commentDoctor;
    private String diagnosis;
    private String dateTime;
    private String appointmentCode;


    public Map<String, String> asMap() {
        Map<String, String> result = new HashMap<>();
       ofNullable(medicalRecordCode).ifPresent(value-> result.put("medicalRecordCode", value));
       ofNullable(commentDoctor).ifPresent(value-> result.put("commentDoctor", value));
       ofNullable(diagnosis).ifPresent(value-> result.put("diagnosis", value));
       ofNullable(dateTime).ifPresent(value-> result.put("dateTime", value));
       ofNullable(appointmentCode).ifPresent(value-> result.put("appointmentCode", value));

        return result;
    }
}
