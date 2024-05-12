 package moj.project.api.dto.mapper;

 import moj.project.api.dto.ScheduleDTO;
 import moj.project.domain.Schedule;
 import org.mapstruct.Mapper;

 import java.util.UUID;

 @Mapper(componentModel = "spring")
 public interface ScheduleMapper extends OffsetDateTimeMapper {

    default ScheduleDTO map(Schedule schedule){
        return ScheduleDTO.builder()
                .scheduleId(schedule.getScheduleId())
                .scheduleCode(schedule.getScheduleCode())
                .dateTime(mapOffsetDateTimeToString(schedule.getDateTime()))
                .duration(schedule.getDuration())
                .availability(schedule.getAvailability())
                .build();
   }


     default Schedule map(ScheduleDTO scheduleDTO){
         return Schedule.builder()
             .scheduleCode(UUID.randomUUID().toString())
             .dateTime(mapStringToOffsetDateTime(scheduleDTO.getDate(), scheduleDTO.getTime()))
             .duration(scheduleDTO.getDuration())
             .availability(true)
             .build();
     }
 }
