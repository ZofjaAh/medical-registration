package moj.project.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import moj.project.api.dto.AppointmentDTO;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.MedicalRecordDTO;
import moj.project.api.dto.mapper.AppointmentMapper;
import moj.project.businnes.AppointmentService;
import moj.project.domain.Appointment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
@RequestMapping(AppointmentController.APPOINTMENT)
public class AppointmentController {
    static final String APPOINTMENT = "/appointment";
    static final String APPOINTMENT_CHANGE_EXECUTION = "/doctor/change/execution/{appointmentCode}";
    static final String MAKE_APPOINTMENT = "/patient/make";
    static final String SHOW_APPOINTMENT_INFORMATION = "/doctor/{appointmentCode}";
    static final String SHOW_APPOINTMENTS = "/patient/show/all";
    static final String APPOINTMENT_DELETE = "/patient/delete/{appointmentCode}";
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    @GetMapping(SHOW_APPOINTMENT_INFORMATION)
    public String showAppointmentInformation(
            @PathVariable String appointmentCode,
            Model model
            ){
        AppointmentInformationDTO appointmentInformationDTO = appointmentMapper.map(
                appointmentService.findAppointmentInformationByAppointmentCode(appointmentCode));


        model.addAttribute("appointmentInformationDTO", appointmentInformationDTO);
        model.addAttribute("medicalRecordDTO", new MedicalRecordDTO());
        model.addAttribute("appointmentCode", appointmentCode);
        return "appointment_information";
    }

    @PostMapping(MAKE_APPOINTMENT)
    public String makeAppointment(
            @Valid @ModelAttribute("appointmentDTO") AppointmentDTO appointmentDTO,
            ModelMap model){
        if(existingPatientEmailExists(appointmentDTO.getExistingPatientEmail())){
           return notFirstAppointmentCreating(appointmentDTO, model);
        }else
          return   firstAppointmentCreating(appointmentDTO, model);

    }

    private String firstAppointmentCreating(
             AppointmentDTO appointmentDTO, ModelMap model) {
        Appointment appointment = appointmentService.createFirstTimeBook(appointmentMapper.map(appointmentDTO));
        model.addAttribute("patientName", appointmentDTO.getPatientName());
        model.addAttribute("patientSurname", appointmentDTO.getPatientSurname());
        model.addAttribute("appointmentCode", appointment.getAppointmentCode());


        return "appointment_done";
    }

    private String notFirstAppointmentCreating(AppointmentDTO appointmentDTO, ModelMap model) {
        Appointment appointment = appointmentService.createNextTimeBook(appointmentMapper.map(appointmentDTO));
        model.addAttribute("existingPatientEmail", appointmentDTO.getExistingPatientEmail());
        model.addAttribute("appointmentCode", appointment.getAppointmentCode());


        return "appointment_done";
    }

    @GetMapping(SHOW_APPOINTMENTS)
    public String showAppointments(
            @RequestParam(value = "existingPatientEmail", required = false)String existingPatientEmail,
            Model model){
      List<AppointmentInformationDTO> allAppointmentDTOs= appointmentService.findAllAppointmentsByPatientEmail(existingPatientEmail)
              .stream().map(appointmentMapper::map).toList();
      var activeAppointmentDTOs = allAppointmentDTOs.stream().filter(this::isActive).toList();
      var nonActiveAppointmentDTOs = allAppointmentDTOs.stream().filter(this::isNonActive).toList();

        model.addAttribute( "nonActiveAppointmentDTOs", nonActiveAppointmentDTOs);
        model.addAttribute(      "activeAppointmentDTOs", activeAppointmentDTOs);
        return "appointments";

    }
    @RequestMapping(value = APPOINTMENT_CHANGE_EXECUTION, method = {RequestMethod.GET, RequestMethod.PATCH})
    public ModelAndView changeAppointmentExecution(
            @PathVariable String appointmentCode,
            ModelMap model){
        appointmentService.changeAppointmentExecutionByAppointmentCode(appointmentCode);
       model.addAttribute("appointmentCode", appointmentCode);
       model.addAttribute("medicalRecordDTO", new MedicalRecordDTO());
        return new ModelAndView( "redirect:/appointment/doctor/{appointmentCode}", model);
    }
    @DeleteMapping(APPOINTMENT_DELETE)
    public ModelAndView deleteAppointment
            (@PathVariable String appointmentCode,
             @RequestParam (value = "existingPatientEmail") String existingPatientEmail,
             ModelMap model){
        appointmentService.deleteByAppointmentCode(appointmentCode);
      model.addAttribute("existingPatientEmail", existingPatientEmail);

        return new ModelAndView("redirect:/appointment/patient/show/all", model);
    }


    private boolean isActive(AppointmentInformationDTO appointment) {
        return appointment.getExecution().equals(false) & appointment.getScheduleDateTime().isAfter(OffsetDateTime.now(ZoneOffset.UTC));
    }
    private boolean isNonActive(AppointmentInformationDTO appointment) {
      return  appointment.getExecution().equals(true) | appointment.getScheduleDateTime().isBefore(OffsetDateTime.now(ZoneOffset.UTC));

    }

    private static boolean existingPatientEmailExists(String email) {
        return Objects.nonNull(email) && !email.isBlank();
    }

}
