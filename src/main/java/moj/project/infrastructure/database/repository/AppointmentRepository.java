package moj.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import moj.project.businnes.dao.AppointmentDAO;
import moj.project.domain.Appointment;
import moj.project.infrastructure.database.entity.AppointmentEntity;
import moj.project.infrastructure.database.repository.jpa.AppointmentJpaRepository;
import moj.project.infrastructure.database.repository.mapper.AppointmentEntityMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@AllArgsConstructor
public class AppointmentRepository implements AppointmentDAO {
    private final AppointmentJpaRepository appointmentJpaRepository;
    private final AppointmentEntityMapper appointmentEntityMapper;
    @Override
    @Transactional
    public Appointment saveAppointment(Appointment appointment) {
        AppointmentEntity appointmentToSave = appointmentEntityMapper.mapToEntity(appointment);
     return appointmentEntityMapper.mapFromEntity(appointment.getAppointmentCode(),
             appointmentJpaRepository.saveAndFlush(appointmentToSave));

    }

    @Override
    public void deleteByAppointmentCode(String appointmentCode) {
        appointmentJpaRepository.deleteByAppointmentCode(appointmentCode);
    }

    @Override
    public List<Appointment> findAllAppointmentsByPatientEmail(String email) {
        return appointmentJpaRepository.findAllByPatientEmail(email).stream()
                .map(appointmentEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Appointment findAppointmentInformationByAppointmentCode(String appointmentCode) {
        AppointmentEntity appointmentInformationByAppointmentCode = appointmentJpaRepository.findAppointmentInformationByAppointmentCode(appointmentCode);
        return appointmentEntityMapper.mapFromEntity(appointmentCode, appointmentInformationByAppointmentCode );
    }



    @Override
    public Appointment findAppointmentByAppointmentCode(String appointmentCode) {
        return appointmentEntityMapper.mapFromEntity(appointmentJpaRepository
                .findAppointmentByAppointmentCode(appointmentCode));

    }




}
