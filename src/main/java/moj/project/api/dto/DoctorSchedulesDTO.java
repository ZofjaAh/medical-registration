package moj.project.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorSchedulesDTO {
   private List<DoctorScheduleDTO.ScheduleDTO> schedules;


}
