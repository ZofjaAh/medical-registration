package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.api.dto.mapper.ScheduleMapper;
import moj.project.businnes.DoctorService;
import moj.project.businnes.ScheduleService;
import moj.project.domain.Doctor;
import moj.project.domain.Schedule;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SearchController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class SearchControllerWebMvcTest {
    private MockMvc mockMvc;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private DoctorMapper doctorMapper;
    @MockBean
    private ScheduleService scheduleService;
    @MockBean
    private ScheduleMapper scheduleMapper;

    @Test
    void retrievingDoctorsWorksCorrectly()throws Exception{
        //given //when
        when(doctorService.findAvailableDoctors()).thenReturn(List.of(DomainFixtures.someDoctor1(),DomainFixtures.someDoctor2()));
        when(doctorMapper.map(any(Doctor.class))).thenReturn(DtoFixtures.someDoctor1());
        when(doctorMapper.map(any(Doctor.class))).thenReturn(DtoFixtures.someDoctor2());
        //then
        mockMvc.perform(get(SearchController.SEARCH))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("availableDoctorDTOs"))
                .andExpect(view().name("search"));

    }
    @Test
    void retrievingAvailableTimeSlotsWorksCorrectly()throws Exception{
        //given
        String doctorCode = "i8j5hs90-j593-547d-djg4-lk84db7987k6";
        //when
        when(doctorService.findDoctor(doctorCode)).thenReturn(DomainFixtures.someDoctor1());
        when(doctorMapper.map(any(Doctor.class))).thenReturn(DtoFixtures.someDoctor2());
        when(scheduleService.findAvailableByDoctorCode(doctorCode)).thenReturn(List.of(DomainFixtures.someSchedule1(), DomainFixtures.someSchedule2()));
        when(scheduleMapper.map(any(Schedule.class))).thenReturn(DtoFixtures.someScheduleTimeSlot1());
        when(scheduleMapper.map(any(Schedule.class))).thenReturn(DtoFixtures.someScheduleTimeSlot2());
        //then
        mockMvc.perform(get( SearchController.SEARCH_TIME_SLOTS, doctorCode)
                        .queryParam("doctorCode", doctorCode)
                        )
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctorDTO"))
                .andExpect(model().attributeExists("appointmentDTO"))
                .andExpect(model().attributeExists("availableScheduleDTOs"))
                .andExpect(view().name("time_slot"));

    }

}