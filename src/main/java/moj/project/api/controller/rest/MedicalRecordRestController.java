package moj.project.api.controller.rest;

import lombok.AllArgsConstructor;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.MedicalRecordDTO;
import moj.project.api.dto.mapper.AppointmentMapper;
import moj.project.businnes.AppointmentService;
import moj.project.businnes.MedicalRecordService;
import moj.project.domain.Appointment;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(MedicalRecordRestController.API_RECORD)
public class MedicalRecordRestController {
    public static final String API_RECORD= "/api/record";
    public static final String ADD_MEDICAL_RECORD= "/add/{appointmentCode}";
    private final MedicalRecordService medicalRecordService;
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    @PostMapping(ADD_MEDICAL_RECORD)

    public AppointmentInformationDTO addMedicalRecord(
            @PathVariable String appointmentCode,
            @RequestBody MedicalRecordDTO medicalRecordDTO){
        Appointment appointment = appointmentService.findAppointmentByAppointmentCode(appointmentCode);

        medicalRecordService.saveMedicalRecord(appointment, medicalRecordDTO);
        return appointmentMapper.map(appointmentService.findAppointmentInformationByAppointmentCode(appointmentCode));
    }
}


