package moj.project.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import moj.project.infrastructure.database.entity.DoctorEntity;
import moj.project.infrastructure.database.entity.ScheduleEntity;
import moj.project.util.EntityFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static moj.project.util.EntityFixtures.someAppointment0_1;

@AllArgsConstructor(onConstructor = @__(@Autowired))
class ScheduleJpaRepositoryTest extends AbstractJpa {
    private ScheduleJpaRepository scheduleJpaRepository;

    @Test
    void thatScheduleShouldSavedCorrectly(){
        //given
        ScheduleEntity scheduleEntity = EntityFixtures.someSchedule1();
        String scheduleCode = scheduleEntity.getScheduleCode();

        scheduleJpaRepository.saveAndFlush(scheduleEntity);
        //when
        var savedEntity = scheduleJpaRepository.findByScheduleCode(scheduleCode);

        //then
        Assertions.assertThat(savedEntity.isPresent()).isEqualTo(true);
    }
    @Test
    void thatScheduleDeletedCorrectly(){
        //given
        ScheduleEntity scheduleEntity1 = EntityFixtures.someSchedule1();
        ScheduleEntity scheduleEntity2 = EntityFixtures.someSchedule1_1();
        String scheduleCode = scheduleEntity1.getScheduleCode();
         List<ScheduleEntity> schedules =  List.of(scheduleEntity1, scheduleEntity2);
   scheduleJpaRepository.saveAllAndFlush(schedules);
   //when
        List<ScheduleEntity> allBeforeDelete = scheduleJpaRepository.findAll();
        scheduleJpaRepository.deleteByScheduleCode(scheduleCode);
        List<ScheduleEntity> allAfterDelete = scheduleJpaRepository.findAll();
Assertions.assertThat(allBeforeDelete).hasSize(8);
allBeforeDelete.removeAll(allAfterDelete);
Assertions.assertThat(allBeforeDelete.get(0).getScheduleCode()).isEqualTo(scheduleCode);
Assertions.assertThat(allAfterDelete).hasSize(7);
    }
@Test
    void thatSearchingSchedulesByDoctorCodeWorksCorrectly(){
        //given
   DoctorEntity searchingDoctor = EntityFixtures.someDoctor0_1();

//when
    List<ScheduleEntity> availableByDoctorCode = scheduleJpaRepository.findAvailableByDoctorCode(searchingDoctor.getDoctorCode());
    //then
    Assertions.assertThat(availableByDoctorCode.size()).isEqualTo(2);
}
    @Test
    void thatSearchingSchedulesByAppointmentCodeWorksCorrectly(){
        //given
        String appointmentCode = someAppointment0_1().getAppointmentCode();

        //when
        Optional<ScheduleEntity> byAppointmentCode = scheduleJpaRepository.findByAppointmentCode(appointmentCode);
        Assertions.assertThat(byAppointmentCode.isPresent()).isEqualTo(true);
    }}