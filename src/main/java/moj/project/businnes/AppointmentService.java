package moj.project.businnes;

import lombok.AllArgsConstructor;
import moj.project.businnes.dao.AppointmentDAO;
import moj.project.domain.*;
import moj.project.domain.exception.ProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final ScheduleService scheduleService;
    private final PatientService patientService;
    private final AppointmentDAO appointmentDAO;
    @Transactional
   public Appointment createFirstTimeBook(AppointmentRequest appointmentRequest) {
        Schedule schedule = scheduleService.findScheduleByScheduleCode(appointmentRequest.getScheduleCode());
        Appointment appointment = buildAppointment(appointmentRequest);

        Patient patientToSave = buildPatient(appointmentRequest, appointment);
        Patient patient = patientService.savePatient(patientToSave);
        Schedule scheduleSaved = scheduleService.save(schedule.withAvailability(false));
        appointmentDAO.saveAppointment(appointment
                .withSchedule(scheduleSaved.withAppointment(appointment))
                .withPatient(patient.withAppointments(Set.of(appointment))));
        return appointment;

    }
    @Transactional

    public Appointment createNextTimeBook(AppointmentRequest appointmentRequest) {
        Schedule schedule = scheduleService.findScheduleByScheduleCode(appointmentRequest.getScheduleCode());
        Patient existingPatient = patientService.findPatient(appointmentRequest.getExistingPatientEmail());
        Appointment appointment = buildAppointment(appointmentRequest);
       Set<Appointment> existingAppointments  = existingPatient.getAppointments();
       existingAppointments.add(appointment);
        Schedule scheduleSaved = scheduleService.save(schedule.withAvailability(false));
        appointmentDAO.saveAppointment(appointment
                        .withSchedule(scheduleSaved.withAppointment(appointment))
                 .withPatient(existingPatient.withAppointments(existingAppointments)));
        return appointment;

    }



    private Appointment buildAppointment( AppointmentRequest appointmentRequest) {
        return Appointment.builder()
                .appointmentCode(UUID.randomUUID().toString())
                .commentPatient(appointmentRequest.getCommentPatient())
                .execution(false)
                .build();
    }

    private Patient buildPatient(AppointmentRequest appointmentRequest, Appointment appointment) {
        return Patient.builder()
                .name(appointmentRequest.getPatientName())
                .surname(appointmentRequest.getPatientSurname())
                .pesel(appointmentRequest.getPatientPesel())
                .gender(appointmentRequest.getPatientGender())
                .age(appointmentRequest.getPatientAge())
                .phone(appointmentRequest.getPatientPhone())
                .email(appointmentRequest.getPatientEmail())
                .address(Address.builder()
                        .country(appointmentRequest.getPatientAddressCountry())
                        .city(appointmentRequest.getPatientAddressCity())
                        .postalCode(appointmentRequest.getPatientAddressPostalCode())
                        .streetAddress(appointmentRequest.getPatientAddressStreet())
                        .build())


        .build();
    }
@Transactional
    public void deleteByAppointmentCode(String appointmentCode) {
        scheduleService.changeAvailability(appointmentCode);
        appointmentDAO.deleteByAppointmentCode(appointmentCode);
    }
@Transactional
    public List<Appointment> findAllAppointmentsByPatientEmail(String email) {
        patientService.findPatient(email);

        return appointmentDAO.findAllAppointmentsByPatientEmail(email);
    }
@Transactional
    public Appointment findAppointmentInformationByAppointmentCode(String appointmentCode) {
        return appointmentDAO.findAppointmentInformationByAppointmentCode(appointmentCode);
    }


@Transactional
    public Appointment changeAppointmentExecutionByAppointmentCode(String appointmentCode) {
      Appointment appointment =  appointmentDAO.findAppointmentByAppointmentCode(appointmentCode);
     if(!appointment.getExecution()){
         return  appointmentDAO.saveAppointment(appointment.withExecution(true));

     }else throw new ProcessingException(
             "Changing Appointment Execution with Appointment Code: [%s] is impossible, execution has already been changed"
                     .formatted(appointmentCode));

    }
@Transactional
    public Appointment findAppointmentByAppointmentCode(String appointmentCode) {
        return appointmentDAO.findAppointmentByAppointmentCode(appointmentCode);
    }



}
