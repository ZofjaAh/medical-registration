package moj.project.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import moj.project.infrastructure.database.entity.AppointmentEntity;
import moj.project.infrastructure.database.entity.DoctorEntity;
import moj.project.infrastructure.database.entity.ScheduleEntity;
import moj.project.util.EntityFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static moj.project.util.EntityFixtures.someDoctor1;


@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorJpaRepositoryTest extends AbstractJpa{
    private DoctorJpaRepository doctorJpaRepository;

    @Test
    void thatDoctorCreatedCorrectly(){
        //given
        DoctorEntity doctorEntity = someDoctor1();
        String doctorCode = doctorEntity.getDoctorCode();
        doctorJpaRepository.saveAndFlush(doctorEntity);

        //when
        var savedEntity = doctorJpaRepository.findByDoctorCode(doctorCode);

        //then
        Assertions.assertThat(savedEntity.isPresent()).isEqualTo(true);
    }
    @Test
    void thatSearchingAvailableDoctorsWorksCorrectly() {
        //given
        //when
        Set<DoctorEntity> availableDoctors = doctorJpaRepository.findAvailable();

        //then
        Assertions.assertThat(availableDoctors.size()).isEqualTo(2);

    }
    @Test
    void thatSearchingDoctorScheduleWorksCorrectly(){
        //given
        String medicalRecordCode = "kj9l3lk4-kl4k-al5l-f2y7-kl3mn77bg3sm";
        DoctorEntity doctorEntity = EntityFixtures.someDoctor0_2();

        String doctorEmail = doctorEntity.getEmail();
        //when
       var doctorSchedule = doctorJpaRepository.findDoctorScheduleByEmail(doctorEmail);
        DoctorEntity doctorEntitySearched = doctorSchedule.orElseThrow();
        List<AppointmentEntity> listAppointments = doctorEntitySearched.getSchedules().stream()
                .filter(schedule -> Optional.ofNullable(schedule.getAppointment()).isPresent())
                .map(ScheduleEntity::getAppointment).toList();
        List<String> medicalRecordCodes = listAppointments.stream()
                .filter(appointment -> Optional.ofNullable(appointment.getMedicalRecord()).isPresent())
                .map(appointmentEntity -> appointmentEntity.getMedicalRecord().getMedicalRecordCode())
                .toList();
         //then
        Assertions.assertThat(doctorEntitySearched.getSchedules().size()).isEqualTo(2);
        Assertions.assertThat(listAppointments.size()).isEqualTo(1);
        Assertions.assertThat(medicalRecordCodes).containsAnyOf(medicalRecordCode);
    }
}