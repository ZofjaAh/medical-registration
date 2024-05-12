 package moj.project.api.dto.mapper;

 import moj.project.api.dto.AppointmentDTO;
 import moj.project.api.dto.AppointmentInformationDTO;
 import moj.project.domain.Appointment;
 import moj.project.domain.AppointmentRequest;
 import moj.project.domain.MedicalRecord;
 import org.mapstruct.Mapper;

 import java.util.Objects;

 @Mapper(componentModel = "spring")
 public interface AppointmentMapper extends OffsetDateTimeMapper{
   AppointmentRequest map(AppointmentDTO appointmentDTO);
   default AppointmentDTO mapToDTO(Appointment appointment){
       return AppointmentDTO.builder()
               .appointmentCode(appointment.getAppointmentCode())
               .commentPatient(appointment.getCommentPatient())
               .execution(appointment.getExecution())
               .build();
   };

  default AppointmentInformationDTO map(Appointment appointment) {
      MedicalRecord medicalRecord = appointment.getMedicalRecord();
      return   AppointmentInformationDTO.builder()
              .appointmentCode(appointment.getAppointmentCode())
              .patientName(appointment.getPatient().getName())
              .patientSurname(appointment.getPatient().getSurname())
              .patientPesel(appointment.getPatient().getPesel())
              .patientEmail(appointment.getPatient().getEmail())
              .patientGender(appointment.getPatient().getGender())
              .patientAge(appointment.getPatient().getAge())
              .commentPatient(appointment.getCommentPatient())
              .execution(appointment.getExecution())
              .scheduleDateTime(appointment.getSchedule().getDateTime())
              .medicalRecordCode(Objects.nonNull(medicalRecord)? medicalRecord.getMedicalRecordCode(): null)
              .commentDoctor(Objects.nonNull(medicalRecord)? medicalRecord.getCommentDoctor(): null)
              .doctorNameSurname(appointment.getSchedule().getDoctor().getName() + " " + appointment.getSchedule().getDoctor().getSurname())
              .diagnosis(Objects.nonNull(medicalRecord)? medicalRecord.getDiagnosis(): null)
              .build();
  }

 }
