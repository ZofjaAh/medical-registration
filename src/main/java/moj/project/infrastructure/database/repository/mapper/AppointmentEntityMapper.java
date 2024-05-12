package moj.project.infrastructure.database.repository.mapper;

import moj.project.domain.*;
import moj.project.infrastructure.database.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface AppointmentEntityMapper {

    AppointmentEntity mapToEntity(Appointment appointment);
    @Mapping(target = "schedule.appointment", ignore = true)
    @Mapping(target = "schedule.doctor.speciality", ignore = true)
    @Mapping(target = "schedule.doctor.address", ignore = true)
    @Mapping(target = "schedule.doctor.schedules", ignore = true)
    @Mapping(target = "medicalRecord.appointment", ignore = true)
    @Mapping(target = "patient.address", ignore = true)
    @Mapping(target = "patient.appointments", ignore = true)
        Appointment mapFromEntity(AppointmentEntity appointmentEntity);

    default Appointment mapFromEntity(String appointmentCode, AppointmentEntity appointmentInformationByAppointmentCode){
        return Appointment.builder()
                .appointmentId(appointmentInformationByAppointmentCode.getAppointmentId())
                .appointmentCode(appointmentCode)
                .commentPatient(appointmentInformationByAppointmentCode.getCommentPatient())
                .execution(appointmentInformationByAppointmentCode.getExecution())
                .medicalRecord(Objects.nonNull(appointmentInformationByAppointmentCode.getMedicalRecord())
                        ? MedicalRecord.builder()
                        .medicalRecordCode(appointmentInformationByAppointmentCode.getMedicalRecord().getMedicalRecordCode())
                        .commentDoctor(appointmentInformationByAppointmentCode.getMedicalRecord().getCommentDoctor())
                        .diagnosis(appointmentInformationByAppointmentCode.getMedicalRecord().getDiagnosis())
                        .build()
                        : null)
                .patient(Patient.builder()
                        .name(appointmentInformationByAppointmentCode.getPatient().getName())
                        .surname(appointmentInformationByAppointmentCode.getPatient().getSurname())
                        .pesel(appointmentInformationByAppointmentCode.getPatient().getPesel())
                        .gender(appointmentInformationByAppointmentCode.getPatient().getGender())
                        .age(appointmentInformationByAppointmentCode.getPatient().getAge())
                        .email(appointmentInformationByAppointmentCode.getPatient().getEmail())
                        .build())
                .schedule(Schedule.builder()
                        .dateTime(appointmentInformationByAppointmentCode.getSchedule().getDateTime())
                        .doctor(Doctor.builder()
                                .name(appointmentInformationByAppointmentCode.getSchedule().getDoctor().getName())
                                .surname(appointmentInformationByAppointmentCode.getSchedule().getDoctor().getSurname())
                                .build())
                        .build())

                .build();
    }
}
