package moj.project.businnes.dao;

import moj.project.domain.Appointment;

import java.util.List;

public interface AppointmentDAO {
    Appointment saveAppointment(Appointment appointment);

    void deleteByAppointmentCode(String appointmentCode);

    List<Appointment> findAllAppointmentsByPatientEmail(String email);

    Appointment findAppointmentInformationByAppointmentCode(String appointmentCode);



    Appointment findAppointmentByAppointmentCode(String appointmentCode);


}
