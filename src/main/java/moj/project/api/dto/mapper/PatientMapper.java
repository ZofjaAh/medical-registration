package moj.project.api.dto.mapper;

import moj.project.api.dto.HealthPatientHistoryDTO;
import moj.project.domain.HealthPatientHistory;
import org.mapstruct.Mapper;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface PatientMapper extends OffsetDateTimeMapper {


    default HealthPatientHistoryDTO map(HealthPatientHistory healthPatientHistory) {
        return HealthPatientHistoryDTO.builder()
                .patientPesel(healthPatientHistory.getPatientPesel())
                .appointments(healthPatientHistory.getAppointments().stream()
                        .map(appointment -> HealthPatientHistoryDTO.AppointmentDTO.builder()
                                .appointmentCode(appointment.getAppointmentCode())
                                .scheduleDateTime(mapOffsetDateTimeToString(appointment.getSchedule().getDateTime()))
                                .commentPatient(appointment.getCommentPatient())
                                .execution(appointment.getExecution())
                                .medicalRecordCode(Objects.nonNull(appointment.getMedicalRecord())?
                                        appointment.getMedicalRecord().getMedicalRecordCode()
                                        :null)
                                .doctorNameSurname(appointment.getSchedule().getDoctor().getName()
                                        + " " + appointment.getSchedule().getDoctor().getSurname())
                                .doctorCode(appointment.getSchedule().getDoctor().getDoctorCode())
                                .medicalRecordDateTime(Objects.nonNull(appointment.getMedicalRecord())?
                                        mapOffsetDateTimeToString(appointment.getMedicalRecord().getDateTime())
                                        : null)
                                .medicalRecordCommentDoctor(Objects.nonNull(appointment.getMedicalRecord())?
                                        appointment.getMedicalRecord().getCommentDoctor()
                                        : null)
                                .medicalRecordDiagnosis(Objects.nonNull(appointment.getMedicalRecord())?
                                        appointment.getMedicalRecord().getDiagnosis()
                                        : null)
                                .build())
                        .toList())
                .build();
    }
}
