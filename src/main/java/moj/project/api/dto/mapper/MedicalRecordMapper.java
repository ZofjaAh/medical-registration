package moj.project.api.dto.mapper;

import moj.project.api.dto.MedicalRecordDTO;
import moj.project.domain.MedicalRecord;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper extends OffsetDateTimeMapper {

   default MedicalRecordDTO mapToDTO(MedicalRecord medicalRecord){
       return MedicalRecordDTO.builder()
               .medicalRecordCode(medicalRecord.getMedicalRecordCode())
       .commentDoctor(medicalRecord.getCommentDoctor())
       .diagnosis(medicalRecord.getDiagnosis())
       .dateTime(mapOffsetDateTimeToString(medicalRecord.getDateTime()))
       .appointmentCode(medicalRecord.getAppointment().getAppointmentCode())
               .build();
   }
}


