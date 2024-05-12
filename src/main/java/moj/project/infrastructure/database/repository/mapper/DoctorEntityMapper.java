package moj.project.infrastructure.database.repository.mapper;

import moj.project.domain.*;
import moj.project.infrastructure.database.entity.DoctorEntity;
import moj.project.infrastructure.database.entity.ScheduleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface DoctorEntityMapper {
    @Mapping(target = "address.doctors", ignore = true)
    @Mapping(target = "address.patients", ignore = true)
    @Mapping(target = "speciality.doctors", ignore = true)
    @Mapping(source = "schedules", target = "schedules", qualifiedByName = "mapSchedules")
    Doctor mapFromEntity(DoctorEntity doctorEntity);
    @Named("mapSchedules")
    @SuppressWarnings("unused")
    default Set<Schedule> mapSchedules(Set<ScheduleEntity> scheduleEntities) {
       if( Objects.isNull(scheduleEntities)) {
           return null;
       }else return scheduleEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }
    @Mapping(target = "appointment", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    Schedule mapFromEntity(ScheduleEntity entity);

    DoctorEntity mapToEntity(Doctor doctor);


   default DoctorSchedule mapFromDoctorEntity(DoctorEntity doctorEntity){
        return DoctorSchedule.builder()
                .doctorNameSurname(doctorEntity.getName()+ " " + doctorEntity.getSurname())
                .doctorCode(doctorEntity.getDoctorCode())
                .doctorEmail(doctorEntity.getEmail())
                .schedules(doctorEntity.getSchedules().stream()
                        .map(scheduleEntity-> DoctorSchedule.Schedule.builder()
                                .scheduleCode(scheduleEntity.getScheduleCode())
                                .dateTime(scheduleEntity.getDateTime())
                                .duration(scheduleEntity.getDuration())
                                .availability(scheduleEntity.getAvailability())
                                .appointment(createAppointment(scheduleEntity))
                                .medicalRecord(createMedicalRecord(scheduleEntity))
                                .build())
                        .toList())
                .build();
    }

    private static MedicalRecord createMedicalRecord(ScheduleEntity scheduleEntity) {
       if(Objects.nonNull(scheduleEntity.getAppointment())){
            if(Objects.nonNull(scheduleEntity.getAppointment().getMedicalRecord())) {
                return MedicalRecord.builder()
                        .medicalRecordCode(scheduleEntity.getAppointment().getMedicalRecord().getMedicalRecordCode())
                        .build();
            }
        }
      return MedicalRecord.builder().build();}


    private static Appointment createAppointment(ScheduleEntity scheduleEntity) {
        return Optional.ofNullable(scheduleEntity.getAppointment()).isPresent() ?
                Appointment.builder()
                        .appointmentCode(scheduleEntity.getAppointment().getAppointmentCode())
                        .execution(scheduleEntity.getAppointment().getExecution())
                        .build() : Appointment.builder().build();
    }


}
