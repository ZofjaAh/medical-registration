package moj.project.api.controller.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import moj.project.api.dto.AppointmentDTO;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.AppointmentsDTO;
import moj.project.api.dto.mapper.AppointmentMapper;
import moj.project.businnes.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping(AppointmentRestController.API_APPOINTMENT)
public class AppointmentRestController {
   public static final String API_APPOINTMENT = "/api/appointment";
    public static final String SHOW_APPOINTMENT_INFORMATION = "/{appointmentCode}";
    public static final String MAKE_APPOINTMENT = "/make";
    public static final String SHOW_ACTIVE_APPOINTMENTS = "/show/active";
    public static final String SHOW_NOT_ACTIVE_APPOINTMENTS = "/show/not-active";
    public static final String APPOINTMENT_DELETE = "/delete/{appointmentCode}";
    public static final String APPOINTMENT_CHANGE_EXECUTION = "/change/execution/{appointmentCode}";

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    @GetMapping(value = SHOW_APPOINTMENT_INFORMATION)
    public AppointmentInformationDTO showAppointmentInformation(
            @PathVariable String appointmentCode){
        return appointmentMapper.map(
                appointmentService.findAppointmentInformationByAppointmentCode(appointmentCode));

    }
    @PostMapping(MAKE_APPOINTMENT)
    public AppointmentDTO makeAppointment(
            @Valid @RequestBody AppointmentDTO appointmentDTO
            ){
        if(existingPatientEmailExists(appointmentDTO.getExistingPatientEmail())){
        return notFirstAppointmentCreating(appointmentDTO);
    }else
        return   firstAppointmentCreating(appointmentDTO);
    }
    private AppointmentDTO firstAppointmentCreating(
             AppointmentDTO appointmentDTO) {
        return appointmentMapper
                .mapToDTO(appointmentService.createFirstTimeBook(appointmentMapper.map(appointmentDTO)));
    }
    private AppointmentDTO notFirstAppointmentCreating(AppointmentDTO appointmentDTO) {
        return appointmentMapper
                .mapToDTO(appointmentService.createNextTimeBook(appointmentMapper.map(appointmentDTO)));
    }
        @GetMapping(SHOW_ACTIVE_APPOINTMENTS)
    public  AppointmentsDTO showActiveAppointments(
            @RequestParam(value = "existingPatientEmail", required = false)String existingPatientEmail){
        return AppointmentsDTO.builder()
                .appointments(getAllAppointments(existingPatientEmail).stream().filter(this::isActive).toList())
                .build();

    }
    @GetMapping(SHOW_NOT_ACTIVE_APPOINTMENTS)
    public AppointmentsDTO showNotActiveAppointments(
            @RequestParam(value = "existingPatientEmail", required = false)String existingPatientEmail){
       return AppointmentsDTO.builder()
               .appointments(getAllAppointments(existingPatientEmail).stream().filter(this::isNonActive).toList())
               .build();
    }
    @DeleteMapping(APPOINTMENT_DELETE)
    public AppointmentsDTO deleteAppointment(
            @PathVariable String appointmentCode,
            @RequestParam (value = "existingPatientEmail", required = false) String existingPatientEmail
    ){
      appointmentService.deleteByAppointmentCode(appointmentCode);
        return AppointmentsDTO.builder()
                .appointments(getAllAppointments(existingPatientEmail))
                .build();
    }
    @PatchMapping(APPOINTMENT_CHANGE_EXECUTION)
    public AppointmentInformationDTO changeAppointmentExecution(
            @PathVariable String appointmentCode){
        return appointmentMapper
                .map(appointmentService.changeAppointmentExecutionByAppointmentCode(appointmentCode));

    }

    private List<AppointmentInformationDTO> getAllAppointments(String existingPatientEmail) {
        return appointmentService.findAllAppointmentsByPatientEmail(existingPatientEmail)
                .stream().map(appointmentMapper::map).toList();
    }
    private boolean isActive(AppointmentInformationDTO appointment) {

        return appointment.getExecution().equals(false) & appointment.getScheduleDateTime().isAfter(OffsetDateTime.now(ZoneOffset.UTC));
    }
    private boolean isNonActive(AppointmentInformationDTO appointment) {
        OffsetDateTime nowTime = OffsetDateTime.now(ZoneOffset.UTC);
        return appointment.getExecution().equals(true) | appointment.getScheduleDateTime().isBefore(nowTime) ;
    }
    private static boolean existingPatientEmailExists(String email) {
        return Objects.nonNull(email) && !email.isBlank();
    }


}
