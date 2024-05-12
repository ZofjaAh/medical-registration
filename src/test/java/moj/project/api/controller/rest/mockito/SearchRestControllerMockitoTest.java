package moj.project.api.controller.rest.mockito;

import moj.project.api.controller.rest.SearchRestController;
import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.DoctorsDTO;
import moj.project.api.dto.ScheduleDTO;
import moj.project.api.dto.SchedulesDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.api.dto.mapper.ScheduleMapper;
import moj.project.businnes.DoctorService;
import moj.project.businnes.ScheduleService;
import moj.project.domain.Doctor;
import moj.project.domain.Schedule;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchRestControllerMockitoTest {
    @Mock
    private DoctorService doctorService;
    @Mock
    private DoctorMapper doctorMapper;
    @Mock
    private ScheduleService scheduleService;
    @Mock
    private ScheduleMapper scheduleMapper;
    @InjectMocks
    private SearchRestController searchRestController;
    @Test
    void thatRetrievingDoctorsWorksCorrectly(){
        //given
        List<DoctorDTO> availableDoctors = List.of(DtoFixtures.someDoctor1(),DtoFixtures.someDoctor2());
        Doctor doctor1 = DomainFixtures.someDoctor1();
        Doctor doctor2 = DomainFixtures.someDoctor2();
        //when
        when(doctorService.findAvailableDoctors()).thenReturn(List.of(doctor1, doctor2));
        when(doctorMapper.map(doctor1)).thenReturn(DtoFixtures.someDoctor1());
        when(doctorMapper.map(doctor2)).thenReturn(DtoFixtures.someDoctor2());
        //then
        DoctorsDTO result = searchRestController.showDoctors();
        Assertions.assertThat(result.getDoctors().size()).isEqualTo(availableDoctors.size());
    }
    @Test
    void thatRetrievingAvailableTimeSlotsWorksCorrectly(){
        //given
        String doctorCode = "i8j5hs90-j593-547d-djg4-lk84db7987k6";
        List<ScheduleDTO> availableTimeSlots = List.of(DtoFixtures.someScheduleTimeSlot1()
                ,DtoFixtures.someScheduleTimeSlot2());
        Schedule schedule1 = DomainFixtures.someSchedule1();
        Schedule schedule2 = DomainFixtures.someSchedule2();

        //when
      when(scheduleService.findAvailableByDoctorCode(doctorCode)).thenReturn(List.of(schedule1,schedule2));
        when(scheduleMapper.map(schedule1)).thenReturn(DtoFixtures.someScheduleTimeSlot1());
        when(scheduleMapper.map(schedule2)).thenReturn(DtoFixtures.someScheduleTimeSlot2());
        //then
        SchedulesDTO result = searchRestController.showAvailableTimeSlots(doctorCode);
        Assertions.assertThat(result.getSchedules().size()).isEqualTo(availableTimeSlots.size());
    }

}