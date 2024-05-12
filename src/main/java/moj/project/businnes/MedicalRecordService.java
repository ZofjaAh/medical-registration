package moj.project.businnes;

import lombok.AllArgsConstructor;
import moj.project.api.dto.MedicalRecordDTO;
import moj.project.businnes.dao.MedicalRecordDAO;
import moj.project.domain.Appointment;
import moj.project.domain.MedicalRecord;
import moj.project.domain.exception.ProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordDAO medicalRecordDAO;

@Transactional
    public MedicalRecord saveMedicalRecord(Appointment appointment, MedicalRecordDTO medicalRecordDTO) {

        if(Objects.isNull(appointment.getMedicalRecord())){
            MedicalRecord medicalRecord = MedicalRecord.builder().
                    medicalRecordCode(UUID.randomUUID().toString())
                    .commentDoctor(medicalRecordDTO.getCommentDoctor())
                    .diagnosis(medicalRecordDTO.getDiagnosis())
                    .dateTime(OffsetDateTime.now(ZoneOffset.UTC))
                    .build();
            Appointment appointmentToSave = appointment.withMedicalRecord(medicalRecord);

            return medicalRecordDAO.saveMedicalRecord(medicalRecord.withAppointment(appointmentToSave));


        }else throw new ProcessingException(
                ("Creating Medical Record for Appointment with Code: [%s] is impossible, " +
                        "medicalRecord is already exist")
                .formatted(appointment.getAppointmentCode()));

    }
}
