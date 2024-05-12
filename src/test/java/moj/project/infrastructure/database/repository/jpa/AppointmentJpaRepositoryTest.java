package moj.project.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import moj.project.infrastructure.database.entity.AppointmentEntity;
import moj.project.util.EntityFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static moj.project.util.EntityFixtures.someAppointment1_2;
import static org.assertj.core.api.Assertions.assertThat;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class AppointmentJpaRepositoryTest extends AbstractJpa {
    private AppointmentJpaRepository appointmentJpaRepository;



    @Test

    void thatDeletingAppointmentWorksCorrectly() {
        //given
        AppointmentEntity appointmentEntity = someAppointment1_2();
        String appointmentCode = appointmentEntity.getAppointmentCode();
        appointmentJpaRepository.saveAndFlush(appointmentEntity);
        //when
        List<AppointmentEntity> allAppointmentsBeforeDeleting = appointmentJpaRepository.findAll();
        appointmentJpaRepository.deleteByAppointmentCode(appointmentCode);
        List<AppointmentEntity> allAppointmentsAfterDeleting = appointmentJpaRepository.findAll();
        //then
        allAppointmentsBeforeDeleting.removeAll(allAppointmentsAfterDeleting);
        assertThat(allAppointmentsBeforeDeleting.size()).isEqualTo(1);
        assertThat(allAppointmentsBeforeDeleting.get(0).getAppointmentCode()).isEqualTo(appointmentCode);
    }

    @Test
    void thatSearchingAllAppointmentsByEmailWorksCorrectly() {
        //given
        String existingPatientEmail = EntityFixtures.somePatient0_1().getEmail();
        //when
        List<AppointmentEntity> result = appointmentJpaRepository.findAllByPatientEmail(existingPatientEmail);
        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void thatSaveAndSearchingAppointmentInformationWorksCorrectly() {
        //given
        AppointmentEntity appointment = someAppointment1_2();
        String appointmentCode = appointment.getAppointmentCode();
        String patientEmail = appointment.getPatient().getEmail();
        String scheduleCode = appointment.getSchedule().getScheduleCode();
        //when
        appointmentJpaRepository.saveAndFlush(appointment);
        AppointmentEntity result = appointmentJpaRepository.findAppointmentInformationByAppointmentCode(appointmentCode);
        //then
        assertThat(result.getSchedule().getScheduleCode()).isEqualTo(scheduleCode);
        assertThat(result.getPatient().getEmail()).isEqualTo(patientEmail);

    }

}