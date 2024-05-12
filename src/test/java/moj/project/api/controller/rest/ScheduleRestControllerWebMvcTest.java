package moj.project.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import moj.project.api.dto.DoctorScheduleDTO;
import moj.project.api.dto.DoctorSchedulesDTO;
import moj.project.api.dto.ScheduleDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.api.dto.mapper.ScheduleMapper;
import moj.project.businnes.DoctorService;
import moj.project.businnes.ScheduleService;
import moj.project.domain.DoctorSchedule;
import moj.project.domain.Schedule;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ScheduleRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ScheduleRestControllerWebMvcTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockBean
    private ScheduleService scheduleService;
    @MockBean
    private ScheduleMapper scheduleMapper;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private DoctorMapper doctorMapper;
@Test

void retrievingScheduleWorksCorrectly() throws Exception{
    //given
    String existingDoctorEmail = "borys_zawalnyj@gmail.com";
    DoctorSchedule doctorSchedule = DomainFixtures.someDoctorSchedule1();
    DoctorScheduleDTO doctorScheduleDTO = DtoFixtures.someDoctorSchedule1();
    String responseBody = objectMapper.writeValueAsString(doctorScheduleDTO);
    //when
    when(doctorService.findDoctorScheduleByDoctorEmail(existingDoctorEmail)).thenReturn(doctorSchedule);
    when(doctorMapper.map(doctorSchedule)).thenReturn(doctorScheduleDTO);
    //then
    MvcResult result = mockMvc.perform(get(ScheduleRestController.API_SCHEDULE + ScheduleRestController.SCHEDULE_SHOW)
            .param("existingDoctorEmail", existingDoctorEmail))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.doctorNameSurname", is(doctorScheduleDTO.getDoctorNameSurname())))
            .andExpect(jsonPath("$.doctorCode", is(doctorScheduleDTO.getDoctorCode())))
            .andExpect(jsonPath("$.doctorEmail", is(doctorScheduleDTO.getDoctorEmail())))
            .andExpect(jsonPath("$.schedules", is(doctorScheduleDTO.getSchedules())))
            .andReturn();
    assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
}
    @Test
    void creatingNewTimeSlotWorksCorrectly() throws Exception{
        //given
        String doctorCode = "jk58s9af-587e-4e2c-92e7-8dh54fce1961";
        ScheduleDTO scheduleBody = DtoFixtures.someScheduleTimeSlot1();
        Schedule schedule = DomainFixtures.someSchedule1();
        ScheduleDTO scheduleDTO = DtoFixtures.someScheduleTimeSlot1_1();

        String requestBody = objectMapper.writeValueAsString(scheduleBody);
        String responseBody = objectMapper.writeValueAsString(scheduleDTO);

        //when
       when(scheduleMapper.map(scheduleBody)).thenReturn(schedule);
        when(doctorService.findDoctor(doctorCode)).thenReturn(DomainFixtures.someDoctor1());
        when(scheduleService.save(any())).thenReturn(schedule.withScheduleId(1));
        when(scheduleMapper.map(any(Schedule.class))).thenReturn(scheduleDTO);
        //then
        MvcResult result = mockMvc.perform(post(ScheduleRestController.API_SCHEDULE + ScheduleRestController.SCHEDULE_ADD, doctorCode)
                .queryParam("doctorCode", doctorCode)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId", is(scheduleDTO.getScheduleId())))
                .andExpect(jsonPath("$.scheduleCode", is(scheduleDTO.getScheduleCode())))
                .andExpect(jsonPath("$.dateTime", is(scheduleDTO.getDateTime())))
                .andExpect(jsonPath("$.duration", is(scheduleDTO.getDuration())))
                .andExpect(jsonPath("$.availability", is(scheduleDTO.getAvailability())))
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
    }
    @Test
    void updatingSchedulesTimeSlotWorksCorrectly() throws Exception{
        //given
        ScheduleDTO scheduleBody = DtoFixtures.someScheduleTimeSlot2();
        ScheduleDTO scheduleDTO = DtoFixtures.someScheduleTimeSlot2_1();
        Schedule scheduleAfterUpdate = DomainFixtures.someSchedule1_2();
        String requestBody = objectMapper.writeValueAsString(scheduleBody);
        String responseBody = objectMapper.writeValueAsString(scheduleDTO);
        //when
        when(scheduleService.findScheduleByScheduleCode(scheduleDTO.getScheduleCode()))
                .thenReturn(DomainFixtures.someSchedule1_1());
        when(scheduleService.updateSchedule(any()))
                .thenReturn(scheduleAfterUpdate);
        when(scheduleMapper.map(scheduleAfterUpdate)).thenReturn(scheduleDTO);
        //then
        MvcResult result = mockMvc.perform(put(ScheduleRestController.API_SCHEDULE + ScheduleRestController.SCHEDULE_UPDATE)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.scheduleId", is(scheduleDTO.getScheduleId())))
                .andExpect(jsonPath("$.scheduleCode", is(scheduleDTO.getScheduleCode())))
                .andExpect(jsonPath("$.dateTime", is(scheduleDTO.getDateTime())))
                .andExpect(jsonPath("$.duration", is(scheduleDTO.getDuration())))
                .andExpect(jsonPath("$.availability", is(scheduleDTO.getAvailability())))
                .andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);

    }
    @Test
    void deletingSchedulesTimeSlotWorksCorrectly() throws Exception{
        //given
        String scheduleCode = "i8j5hs90-j593-547d-djg4-lk84db7987k6";
        String doctorEmail = "borys_zawalnyj@gmail.com";
        DoctorSchedulesDTO doctorSchedulesDTO = DtoFixtures.someDoctorSchedules1();
        String responseBody = objectMapper.writeValueAsString(doctorSchedulesDTO);
        List<DoctorScheduleDTO.ScheduleDTO> schedulesAfterDelete = List.of(
                DtoFixtures.someDoctorTimeSlot1(),
                DtoFixtures.someDoctorTimeSlot1(),
                DtoFixtures.someDoctorTimeSlot1());
        //when
        when(doctorService.findDoctorScheduleByDoctorEmail(doctorEmail)).thenReturn(DomainFixtures.someDoctorSchedule1());
        when(doctorMapper.map(any(DoctorSchedule.class))).thenReturn(DtoFixtures.someDoctorSchedule1().withSchedules(schedulesAfterDelete));
        //then
        MvcResult result = mockMvc.perform(delete(ScheduleRestController.API_SCHEDULE +ScheduleRestController.SCHEDULE_DELETE, scheduleCode)
                .param("doctorEmail", doctorEmail)
                .queryParam("scheduleCode", scheduleCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.schedules.size()", is(3)))
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);

}

}