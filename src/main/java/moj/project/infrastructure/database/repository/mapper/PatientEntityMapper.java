package moj.project.infrastructure.database.repository.mapper;

import moj.project.domain.*;
import moj.project.infrastructure.database.entity.AppointmentEntity;
import moj.project.infrastructure.database.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientEntityMapper {
    @Mapping(target = "address.doctors", ignore = true)
    @Mapping(target = "address.patients", ignore = true)
    @Mapping(source = "appointments", target = "appointments", qualifiedByName = "mapAppointments")
    Patient mapFromEntity(PatientEntity patientEntity);

    @Named("mapAppointments")
    @SuppressWarnings("unused")
    default Set<Appointment> mapAppointments(Set<AppointmentEntity> appointmentEntities) {
        return appointmentEntities.stream().map(this::mapFromEntity).collect(Collectors.toSet());
    }

    @Mapping(target = "schedule.appointment", ignore = true)
    @Mapping(target = "schedule.doctor", ignore = true)
    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "medicalRecord", ignore = true)
    Appointment mapFromEntity(AppointmentEntity entity);


    PatientEntity mapToEntity(Patient patient);


    default HealthPatientHistory mapFromEntity(String patientPesel, PatientEntity patientHistory) {
        return HealthPatientHistory.builder()
                .patientPesel(patientPesel)
                .appointments(Objects.nonNull(patientHistory.getAppointments()) ?
                        patientHistory.getAppointments().stream().map(appointment ->
                                HealthPatientHistory.Appointment.builder()
                                        .appointmentCode(appointment.getAppointmentCode())
                                        .commentPatient(appointment.getCommentPatient())
                                        .execution(appointment.getExecution())
                                        .schedule(Schedule.builder()
                                                .dateTime(appointment.getSchedule().getDateTime())
                                                .doctor(Doctor.builder()
                                                        .name(appointment.getSchedule().getDoctor().getName())
                                                        .doctorCode(appointment.getSchedule().getDoctor().getDoctorCode())
                                                        .surname(appointment.getSchedule().getDoctor().getSurname())
                                                        .build())
                                                .build())
                                        .medicalRecord(Objects.nonNull(appointment.getMedicalRecord()) ?
                                                MedicalRecord.builder()
                                                .medicalRecordCode(appointment.getMedicalRecord().getMedicalRecordCode())
                                                .commentDoctor(appointment.getMedicalRecord().getCommentDoctor())
                                                .dateTime(appointment.getMedicalRecord().getDateTime())
                                                .diagnosis(appointment.getMedicalRecord().getDiagnosis()).build()
                                                : null)
                                        .build()).toList()
                        : null)
                .build();
    }

}

