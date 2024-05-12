package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.mapper.AppointmentMapper;
import moj.project.businnes.AppointmentService;
import moj.project.domain.Appointment;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AppointmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AppointmentControllerWebMvcTest {
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private AppointmentMapper appointmentMapper;

    @Test
    void retrievingAppointmentInformationWorksCorrectly() throws Exception {
        //given
        String appointmentCode = "76015b28-f5f5-4b6d-9da9-6141d61881b5";
        Appointment appointment = DomainFixtures.someAppointment1();
        AppointmentInformationDTO appointmentInformationDTO = DtoFixtures.someAppointmentInformation1_1();
        //when
        when(appointmentService.findAppointmentInformationByAppointmentCode(appointmentCode)).thenReturn(appointment);
        when(appointmentMapper.map(appointment)).thenReturn(appointmentInformationDTO);
        //then
      mockMvc.perform(get(AppointmentController.APPOINTMENT + AppointmentController.SHOW_APPOINTMENT_INFORMATION, appointmentCode)
                        .queryParam("appointmentCode", appointmentCode))
                .andExpect(status().isOk())
              .andExpect(model().attributeExists("appointmentInformationDTO"))
                .andExpect(view().name("appointment_information"));;

    }

    @Test
    void creatingAppointmentWorksCorrectly() throws Exception {
        //given
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        DtoFixtures.someAppointment1().asMap().forEach(parameters::add);

        Appointment appointment = DomainFixtures.someAppointment2() ;
        //when
        when(appointmentService.createFirstTimeBook(any())).thenReturn(appointment);
        //then
     mockMvc.perform(post(AppointmentController.APPOINTMENT + AppointmentController.MAKE_APPOINTMENT)
                        .params(parameters) )
                .andExpect(status().isOk())
             .andExpect(model().attributeExists("patientName"))
             .andExpect(model().attributeExists("patientSurname"))
             .andExpect(model().attributeExists("appointmentCode"))
             .andExpect(view().name("appointment_done"));


    }

    @Test
    void showAllAppointmentsCorrectly() throws Exception {
        //given
        String patientEmail = "giorg_zdrowy@gmail.com";

        List<Appointment> appointments = List.of(DomainFixtures.someAppointment1(),
                DomainFixtures.someAppointment1(),
                DomainFixtures.someAppointment1());

        //when
        when(appointmentService.findAllAppointmentsByPatientEmail(patientEmail)).thenReturn(appointments);
        when(appointmentMapper.map(Mockito.any(Appointment.class))).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now().plusHours(3))
                .withExecution(false));//A
        when(appointmentMapper.map(Mockito.any(Appointment.class))).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now().minusHours(10))
                .withExecution(true));//N
        when(appointmentMapper.map(Mockito.any(Appointment.class))).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now().minusHours(6))
                .withExecution(false));//N

        //then
        mockMvc.perform(get(AppointmentController.APPOINTMENT + AppointmentController.SHOW_APPOINTMENTS)
                        .param("existingPatientEmail", patientEmail))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("nonActiveAppointmentDTOs"))
                .andExpect(model().attributeExists("activeAppointmentDTOs"))
                .andExpect(view().name("appointments"));
    }



    @Test
    void deletingAppointmentWithRetrievingAppointmentInformationWorksCorrectly() throws Exception {
        //given
        String appointmentCode = "jkj45lk6-l9s8-fk34-j5al-al35l09d36dk";
        String existingPatientEmail = "jakub_pacjent@gmail.com";
        List<Appointment> appointmentList = List.of(
                DomainFixtures.someAppointment1(),
                DomainFixtures.someAppointment1(),
                DomainFixtures.someAppointment1());
        //when
        when(appointmentService.findAllAppointmentsByPatientEmail(existingPatientEmail)).thenReturn(appointmentList);
        when(appointmentMapper.map(Mockito.any(Appointment.class))).thenReturn(DtoFixtures.someAppointmentInformation1());
        when(appointmentMapper.map(Mockito.any(Appointment.class))).thenReturn(DtoFixtures.someAppointmentInformation1());
        when(appointmentMapper.map(Mockito.any(Appointment.class))).thenReturn(DtoFixtures.someAppointmentInformation1());
        //then
       mockMvc.perform(delete(AppointmentController.APPOINTMENT + AppointmentController.APPOINTMENT_DELETE, appointmentCode)
                        .param("existingPatientEmail", existingPatientEmail)
                        .queryParam("appointmentCode", appointmentCode))
               .andExpect(MockMvcResultMatchers.redirectedUrl("/appointment/patient/show/all?existingPatientEmail=jakub_pacjent%40gmail.com" ))
               .andExpect(status().isFound());

    }

    @Test
    void changingAppointmentExecutionCorrectly() throws Exception {
        //given

        String appointmentCode = "g3kj6828-05f5-fhj5-9da9-614109k881b5";

        Appointment appointment = DomainFixtures.someAppointment1();
        AppointmentInformationDTO appointmentInformationDTO = DtoFixtures.someAppointmentInformation1().withExecution(true);
        //when
        when(appointmentService.changeAppointmentExecutionByAppointmentCode(appointmentCode)).thenReturn(appointment);
        when(appointmentMapper.map(appointment)).thenReturn(appointmentInformationDTO);
        //then


        mockMvc.perform(patch(AppointmentController.APPOINTMENT
                                + AppointmentController.APPOINTMENT_CHANGE_EXECUTION, appointmentCode)

                )
                .andExpect(MockMvcResultMatchers.redirectedUrl("/appointment/doctor/" + appointmentCode))
                .andExpect(status().isFound());


    }

    @Test
    void thatEmailValidationWorksCorrectly() throws Exception {
        // given
        String badEmail = "badEmail";
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> parametersMap = DtoFixtures.someAppointment1().withPatientEmail(badEmail).asMap();
        parametersMap.forEach(parameters::add);

        // when, then
        mockMvc.perform(post(AppointmentController.APPOINTMENT + AppointmentController.MAKE_APPOINTMENT)
                        .params(parameters))
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage",Matchers.containsString(badEmail)))
                .andExpect(view().name("error"));
    }



    @ParameterizedTest
    @MethodSource
    void thatPhoneValidationWorksCorrectly(Boolean correctPhone, String phone) throws Exception {
        // given
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> parametersMap =  DtoFixtures.someAppointment1().withPatientPhone(phone).asMap();
        parametersMap.forEach(parameters::add);

        // when, then
        if (correctPhone) {
            Appointment appointment = DomainFixtures.someAppointment2() ;

            when(appointmentService.createFirstTimeBook(any())).thenReturn(appointment);

          mockMvc.perform(post(AppointmentController.APPOINTMENT + AppointmentController.MAKE_APPOINTMENT)
                            .params(parameters))
                  .andExpect(status().isOk())
                  .andExpect(model().attributeExists("patientName"))
                  .andExpect(model().attributeExists("patientSurname"))
                  .andExpect(model().attributeExists("appointmentCode"))
                  .andExpect(view().name("appointment_done"));

        } else {
            mockMvc.perform(post(AppointmentController.APPOINTMENT + AppointmentController.MAKE_APPOINTMENT)
                            .params(parameters))
                    .andExpect(status().isBadRequest())
                    .andExpect(model().attributeExists("errorMessage"))
                    .andExpect(model().attribute("errorMessage", Matchers.containsString(phone)))
                    .andExpect(view().name("error"));
        }
    }

    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
        return Stream.of(
                Arguments.of(false, ""),
                Arguments.of(false, "+48 504 203 260@@"),
                Arguments.of(false, "+48.504.203.260"),
                Arguments.of(false, "+55(123) 456-78-90-"),
                Arguments.of(false, "+55(123) - 456-78-90"),
                Arguments.of(false, "504.203.260"),
                Arguments.of(false, " "),
                Arguments.of(false, "-"),
                Arguments.of(false, "()"),
                Arguments.of(false, "() + ()"),
                Arguments.of(false, "(21 7777"),
                Arguments.of(false, "+48 (21)"),
                Arguments.of(false, "+"),
                Arguments.of(false, " 1"),
                Arguments.of(false, "1"),
                Arguments.of(false, "+48 (12) 504 203 260"),
                Arguments.of(false, "+48 (12) 504-203-260"),
                Arguments.of(false, "+48(12)504203260"),
                Arguments.of(false, "555-5555-555"),
                Arguments.of(true, "+48 504 203 260")
        );
    }

}