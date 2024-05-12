package moj.project.api.controller.rest.mockito;

import moj.project.api.controller.rest.ScheduleRestController;
import moj.project.api.dto.DoctorScheduleDTO;
import moj.project.api.dto.DoctorSchedulesDTO;
import moj.project.api.dto.ScheduleDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.api.dto.mapper.ScheduleMapper;
import moj.project.businnes.DoctorService;
import moj.project.businnes.ScheduleService;
import moj.project.domain.Doctor;
import moj.project.domain.DoctorSchedule;
import moj.project.domain.Schedule;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScheduleRestControllerMockitoTest {
    @Mock
    private ScheduleService scheduleService;
    @Mock
    private ScheduleMapper scheduleMapper;
    @Mock
    private DoctorService doctorService;
    @Mock
    private DoctorMapper doctorMapper;
    @InjectMocks
    private ScheduleRestController scheduleRestController;
    @Test
    void thatRetrievingScheduleWorksCorrectly(){
        //given
        String existingDoctorEmail = "borys_zawalnyj@gmail.com";
        DoctorSchedule doctorSchedule = DomainFixtures.someDoctorSchedule1();
        //when
        when(doctorService.findDoctorScheduleByDoctorEmail(existingDoctorEmail)).thenReturn(doctorSchedule);
        when(doctorMapper.map(doctorSchedule)).thenReturn(DtoFixtures.someDoctorSchedule1());
        //then
        DoctorScheduleDTO result = scheduleRestController.showSchedule(existingDoctorEmail);
        Assertions.assertThat(result).isEqualTo(DtoFixtures.someDoctorSchedule1());

    }
    @Test
    void thatCreatingNewTimeSlotWorksCorrectly(){
        //given
        String doctorCode = "jk58s9af-587e-4e2c-92e7-8dh54fce1961";
        ScheduleDTO scheduleDTO = DtoFixtures.someScheduleTimeSlot1();
        Schedule schedule = DomainFixtures.someSchedule1();
        Schedule schedule2 = DomainFixtures.someSchedule2();
        Doctor doctor =  DomainFixtures.someDoctor2().withSchedules(Set.of(schedule2));
        Set<Schedule> schedules = Set.of(schedule2, schedule);

        //when
        when(scheduleMapper.map(scheduleDTO)).thenReturn(schedule);
        when(doctorService.findDoctor(doctorCode)).thenReturn(doctor);
        when(scheduleService.save(schedule.withDoctor(doctor.withSchedules(schedules)))).thenReturn(schedule.withScheduleId(1));
        when(scheduleMapper.map(any(Schedule.class))).thenReturn(DtoFixtures.someScheduleTimeSlot1_1());
        //then
        ScheduleDTO result = scheduleRestController.addSchedule(doctorCode,scheduleDTO);
        Assertions.assertThat(result).isEqualTo(DtoFixtures.someScheduleTimeSlot1_1());

    }
     @Test
    void thatUpdatingSchedulesTimeSlotWorksCorrectly(){
        //given
         ScheduleDTO scheduleDTO = DtoFixtures.someScheduleTimeSlot2();
         Schedule schedule = DomainFixtures.someSchedule1_1();
         OffsetDateTime offsetDateTime = OffsetDateTime.of(2025, 1, 23, 13, 00, 00, 00, ZoneOffset.UTC);
         Schedule scheduleAfterUpdate = DomainFixtures.someSchedule1_2();
         //when
         when(scheduleService.findScheduleByScheduleCode(scheduleDTO.getScheduleCode())).thenReturn(schedule);
         when(scheduleMapper.mapStringToOffsetDateTime(scheduleDTO.getDate(), scheduleDTO.getTime())).thenReturn(offsetDateTime);
         when(scheduleService.updateSchedule(DomainFixtures.someSchedule1_1().withDateTime(offsetDateTime).withDuration(20)))
                 .thenReturn(scheduleAfterUpdate);
         when(scheduleMapper.map(scheduleAfterUpdate)).thenReturn(DtoFixtures.someScheduleTimeSlot2_1());
         //then
         ScheduleDTO result = scheduleRestController.updateSchedule(scheduleDTO);
         Assertions.assertThat(result).isEqualTo(DtoFixtures.someScheduleTimeSlot2_1());

     }
     @Test
    void thatDeletingSchedulesTimeSlotWorksCorrectly(){
        //given
         String scheduleCode = "i8j5hs90-j593-547d-djg4-lk84db7987k6";
         String doctorEmail = "borys_zawalnyj@gmail.com";
         List<DoctorScheduleDTO.ScheduleDTO> schedulesAfterDelete = List.of(
                 DtoFixtures.someDoctorTimeSlot1(),
                 DtoFixtures.someDoctorTimeSlot1(),
                 DtoFixtures.someDoctorTimeSlot1());
     //when
         when(doctorService.findDoctorScheduleByDoctorEmail(doctorEmail)).thenReturn(DomainFixtures.someDoctorSchedule1());
        when(doctorMapper.map(any(DoctorSchedule.class))).thenReturn(DtoFixtures.someDoctorSchedule1().withSchedules(schedulesAfterDelete));
         //then
        DoctorSchedulesDTO result = scheduleRestController.deleteSchedule(scheduleCode, doctorEmail);
         Assertions.assertThat(result.getSchedules().size()).isEqualTo(schedulesAfterDelete.size());
    }


}