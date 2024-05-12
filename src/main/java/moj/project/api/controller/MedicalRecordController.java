package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.MedicalRecordDTO;
import moj.project.businnes.AppointmentService;
import moj.project.businnes.MedicalRecordService;
import moj.project.domain.Appointment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@AllArgsConstructor
public class MedicalRecordController {
    static final String ADD_MEDICAL_RECORD = "/appointment/doctor/add/record/{appointmentCode}";
    private final MedicalRecordService medicalRecordService;
    private final AppointmentService appointmentService;
    @PostMapping(ADD_MEDICAL_RECORD)
    public ModelAndView addMedicalRecord(
            @PathVariable String appointmentCode,
            @ModelAttribute("medicalRecordDTO") MedicalRecordDTO medicalRecordDTO,
            ModelMap model){
        Appointment appointment = appointmentService.findAppointmentByAppointmentCode(appointmentCode);

        medicalRecordService.saveMedicalRecord(appointment, medicalRecordDTO);
      model.addAttribute("appointmentCode", appointmentCode);
      model.addAttribute("medicalRecordDTO", new MedicalRecordDTO());
        return   new ModelAndView("redirect:/appointment/doctor/{appointmentCode}", model);

    }




}
