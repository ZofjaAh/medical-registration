package moj.project.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import moj.project.api.dto.DoctorsDTO;
import moj.project.api.dto.SchedulesDTO;
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
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SearchRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class SearchRestControllerWebMvcTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
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
        //given
        DoctorsDTO doctorsDTO = DtoFixtures.someDoctors1();
        String responseBody = objectMapper.writeValueAsString(doctorsDTO);
        Doctor doctor1 = DomainFixtures.someDoctor1();
        Doctor doctor2 = DomainFixtures.someDoctor2();
        //when
        when(doctorService.findAvailableDoctors()).thenReturn(List.of(doctor1, doctor2));
        when(doctorMapper.map(doctor1)).thenReturn(DtoFixtures.someDoctor1());
        when(doctorMapper.map(doctor2)).thenReturn(DtoFixtures.someDoctor2());
        //then
        MvcResult result = mockMvc.perform(get(SearchRestController.API_SEARCH + SearchRestController.DOCTORS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.doctors.size()", is(2)))
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }
    @Test
    void retrievingAvailableTimeSlotsWorksCorrectly()throws Exception{
        //given
        String doctorCode = "i8j5hs90-j593-547d-djg4-lk84db7987k6";
        SchedulesDTO schedulesDTO = DtoFixtures.someSchedules1();
        String responseBody = objectMapper.writeValueAsString(schedulesDTO);
        Schedule schedule1 = DomainFixtures.someSchedule1();
        Schedule schedule2 = DomainFixtures.someSchedule2();
        //when
        when(scheduleService.findAvailableByDoctorCode(doctorCode)).thenReturn(List.of(schedule1, schedule2 ));
        when(scheduleMapper.map(schedule1)).thenReturn(DtoFixtures.someScheduleTimeSlot1());
        when(scheduleMapper.map(schedule2)).thenReturn(DtoFixtures.someScheduleTimeSlot2());
        //then
        MvcResult result = mockMvc.perform(
                get(SearchRestController.API_SEARCH + SearchRestController.SHOW_TIME_SLOTS, doctorCode)
                        .queryParam("doctorCode", doctorCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schedules.size()", is(2)))
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }

}