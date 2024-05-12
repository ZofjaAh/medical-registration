package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.DoctorScheduleDTO;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static moj.project.util.DomainFixtures.someDoctor1;
import static moj.project.util.DomainFixtures.someSchedule1;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ScheduleController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ScheduleControllerWebMvcTest {
    private MockMvc mockMvc;
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
    DoctorScheduleDTO doctorScheduleDTO = DtoFixtures.someDoctorSchedule1().withSchedules(
            List.of(DtoFixtures.someScheduleTimeSlotDoctorSchedule0_1()));
    //when
    when(doctorService.findDoctorScheduleByDoctorEmail(existingDoctorEmail)).thenReturn(doctorSchedule);
    when(doctorMapper.map(doctorSchedule)).thenReturn(doctorScheduleDTO);
    //then
     mockMvc.perform(get(ScheduleController.SCHEDULE_SHOW)
            .param("existingDoctorEmail", existingDoctorEmail))
            .andExpect(status().isOk())
             .andExpect(model().attributeExists("doctorScheduleDTO"))
             .andExpect(model().attributeExists("availableScheduleCodes"))
             .andExpect(model().attributeExists("scheduleDTO"))
             .andExpect(view().name("schedule"));

}
    @Test
    void creatingNewTimeSlotWorksCorrectly() throws Exception{
        //given
        String doctorCode = "jk58s9af-587e-4e2c-92e7-8dh54fce1961";
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        ScheduleDTO scheduleBody = DtoFixtures.someScheduleTimeSlot1();
        Map<String, String> parametersMap = scheduleBody.asMap();
        parametersMap.forEach(parameters::add);

        Schedule schedule = DomainFixtures.someSchedule1();
        ScheduleDTO scheduleDTO = DtoFixtures.someScheduleTimeSlot1_1();

        //when
        when(scheduleMapper.map(scheduleBody.withDoctorCode(doctorCode))).thenReturn(schedule);
        when(doctorService.findDoctor(doctorCode)).thenReturn(someDoctor1().withSchedules(Set.of(someSchedule1())));
        when(scheduleService.save(any())).thenReturn(DomainFixtures.someSchedule1_1());
        when(scheduleMapper.map(any(Schedule.class))).thenReturn(scheduleDTO);
        //then
         mockMvc.perform(post(ScheduleController.SCHEDULE_ADD, doctorCode)
                .queryParam("doctorCode", doctorCode)
                         .params(parameters))
                .andExpect(status().isOk())
                 .andExpect(model().attributeExists("scheduleDTO"))
                 .andExpect(model().attributeExists("existingDoctorEmail"))
                 .andExpect(view().name("schedule_create"));

    }
    @Test
    void updatingSchedulesTimeSlotWorksCorrectly() throws Exception{
        //given
        ScheduleDTO scheduleDTO = DtoFixtures.someScheduleTimeSlot2_1();
        Schedule scheduleAfterUpdate = DomainFixtures.someSchedule1_2();
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> parametersMap = DtoFixtures.someScheduleTimeSlot2().asMap();
                parametersMap.forEach(parameters::add);
        //when
        when(scheduleService.findScheduleByScheduleCode(any()))
                .thenReturn(DomainFixtures.someSchedule1_1().withDoctor(someDoctor1()));
        when(scheduleService.updateSchedule(any()))
                .thenReturn(scheduleAfterUpdate);
        when(scheduleMapper.map(scheduleAfterUpdate)).thenReturn(scheduleDTO);
        //then
         mockMvc.perform(put(ScheduleController.SCHEDULE_UPDATE)
                         .params(parameters))
                .andExpect(status().isOk())
                 .andExpect(model().attributeExists("scheduleDTO"))
                 .andExpect(model().attributeExists("existingDoctorEmail"))
                 .andExpect(view().name("schedule_update"));


    }
    @Test
    void deletingSchedulesTimeSlotWorksCorrectly() throws Exception{
        //given
        String scheduleCode = "i8j5hs90-j593-547d-djg4-lk84db7987k6";
        String doctorEmail = "borys_zawalnyj@gmail.com";

        //when
        //then
         mockMvc.perform(delete( ScheduleController.SCHEDULE_DELETE, scheduleCode)
                .param("doctorEmail", doctorEmail)
                .queryParam("scheduleCode", scheduleCode))
                 .andExpect(status().isFound())
                 .andExpect(MockMvcResultMatchers
                         .redirectedUrl("/schedule/show?existingDoctorEmail=borys_zawalnyj%40gmail.com"));


}

}