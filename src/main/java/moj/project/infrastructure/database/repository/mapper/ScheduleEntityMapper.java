package moj.project.infrastructure.database.repository.mapper;

import moj.project.domain.Schedule;
import moj.project.infrastructure.database.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface ScheduleEntityMapper {
    @Mapping(target = "appointment.medicalRecord", ignore = true)
    @Mapping(target = "appointment.schedule", ignore = true)
    @Mapping(target = "appointment.patient", ignore = true)
    @Mapping(target = "doctor.speciality", ignore = true)
    @Mapping(target = "doctor.address", ignore = true)
    @Mapping(target = "doctor.schedules", ignore = true)
  Schedule mapFromEntity(ScheduleEntity scheduleEntity);

    ScheduleEntity map(Schedule schedule);
}
