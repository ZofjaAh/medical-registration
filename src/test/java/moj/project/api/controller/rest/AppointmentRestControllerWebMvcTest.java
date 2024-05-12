package moj.project.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import moj.project.api.dto.AppointmentDTO;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.AppointmentsDTO;
import moj.project.api.dto.mapper.AppointmentMapper;
import moj.project.businnes.AppointmentService;
import moj.project.domain.Appointment;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AppointmentRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class AppointmentRestControllerWebMvcTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
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
        String responseBody = objectMapper.writeValueAsString(appointmentInformationDTO);
        //when
        when(appointmentService.findAppointmentInformationByAppointmentCode(appointmentCode)).thenReturn(appointment);
        when(appointmentMapper.map(appointment)).thenReturn(appointmentInformationDTO);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXXXX");
        //then
        MvcResult result = mockMvc.perform(get(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.SHOW_APPOINTMENT_INFORMATION, appointmentCode)
                        .queryParam("appointmentCode", appointmentCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentCode", is(appointmentInformationDTO.getAppointmentCode())))
                .andExpect(jsonPath("$.patientName", is(appointmentInformationDTO.getPatientName())))
                .andExpect(jsonPath("$.patientSurname", is(appointmentInformationDTO.getPatientSurname())))
                .andExpect(jsonPath("$.patientPesel", is(appointmentInformationDTO.getPatientPesel())))
                .andExpect(jsonPath("$.patientEmail", is(appointmentInformationDTO.getPatientEmail())))
                .andExpect(jsonPath("$.patientGender", is(appointmentInformationDTO.getPatientGender())))
                .andExpect(jsonPath("$.patientAge", is(appointmentInformationDTO.getPatientAge())))
                .andExpect(jsonPath("$.commentPatient", is(appointmentInformationDTO.getCommentPatient())))
                .andExpect(jsonPath("$.execution", is(appointmentInformationDTO.getExecution())))
                .andExpect(jsonPath("$.scheduleDateTime", is(appointmentInformationDTO.getScheduleDateTime().format(formatter))))
                .andExpect(jsonPath("$.medicalRecordCode", is(appointmentInformationDTO.getMedicalRecordCode())))
                .andExpect(jsonPath("$.commentDoctor", is(appointmentInformationDTO.getCommentDoctor())))
                .andExpect(jsonPath("$.diagnosis", is(appointmentInformationDTO.getDiagnosis())))
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }

    @Test
    void creatingAppointmentWorksCorrectly() throws Exception {
        //given
        AppointmentDTO appointmentBody = DtoFixtures.someAppointment1();
        AppointmentDTO appointmentDTO = DtoFixtures.someAppointment2();
        String requestBody = objectMapper.writeValueAsString(appointmentBody);
        String responseBody = objectMapper.writeValueAsString(appointmentDTO);
        //when
        when(appointmentMapper.mapToDTO(any())).thenReturn(appointmentDTO);
        //then
        MvcResult result = mockMvc.perform(post(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.MAKE_APPOINTMENT)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentCode", is(appointmentDTO.getAppointmentCode())))
                .andExpect(jsonPath("$.commentPatient", is(appointmentDTO.getCommentPatient())))
                .andExpect(jsonPath("$.execution", is(appointmentDTO.getExecution())))
                .andReturn();
        assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
    }

    @Test
    void showAllActiveAppointmentsCorrectly() throws Exception {
        //given
        Clock clock = Clock.fixed(Instant.parse("2024-08-22T10:00:01Z"), ZoneOffset.UTC);
        OffsetDateTime offsetDateTime = OffsetDateTime.now(clock);
        String patientEmail = "giorg_zdrowy@gmail.com";
        AppointmentsDTO appointmentsDTO = DtoFixtures.someAppointments1();

        Appointment appointment1 = DomainFixtures.someAppointment1().withAppointmentId(1);
        Appointment appointment2 = DomainFixtures.someAppointment1().withAppointmentId(2);
        Appointment appointment3 = DomainFixtures.someAppointment1().withAppointmentId(3);
        List<Appointment> appointments = List.of(appointment1,
                appointment2,
                appointment3);
        String responseBody = objectMapper.writeValueAsString(appointmentsDTO);

        //when
        when(appointmentService.findAllAppointmentsByPatientEmail(patientEmail)).thenReturn(appointments);
        when(appointmentMapper.map(appointment1)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).minusHours(8))
                .withExecution(false));
        when(appointmentMapper.map(appointment2)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).minusHours(6))
                .withExecution(true));
        when(appointmentMapper.map(appointment3)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).plusHours(3))
                .withExecution(false));
        try(MockedStatic<OffsetDateTime> mockedStatic = mockStatic(OffsetDateTime.class)) {
            mockedStatic.when(() -> OffsetDateTime.now(ZoneOffset.UTC)).thenReturn(offsetDateTime);
                       //then
            MvcResult result = mockMvc.perform(get(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.SHOW_ACTIVE_APPOINTMENTS)
                            .param("existingPatientEmail", patientEmail))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.appointments[*].appointmentCode", containsInAnyOrder(appointmentsDTO.getAppointments().get(0).getAppointmentCode())))
                    .andExpect(jsonPath("$.appointments.size()", is(1)))
                    .andReturn();
            assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
        }
    }

    @Test
    void showAllNotActiveAppointmentsCorrectly() throws Exception {
        //given
        Clock clock = Clock.fixed(Instant.parse("2024-08-22T10:00:01Z"), ZoneOffset.UTC);
        OffsetDateTime offsetDateTime = OffsetDateTime.now(clock);
        String patientEmail = "giorg_niezdrowy@gmail.com";
        String appointmentCode1 = "dk9fj4nx-f5f5-4b6d-9da9-6141d61881b5";
        String appointmentCode2 = "98ske87f-f5f5-4b6d-9da9-6141d61881b5";
        AppointmentsDTO appointmentsDTO = DtoFixtures.someAppointments2();
        Appointment appointment1 = DomainFixtures.someAppointment1().withAppointmentId(1);
        Appointment appointment2 = DomainFixtures.someAppointment1().withAppointmentId(2);
        Appointment appointment3 = DomainFixtures.someAppointment1().withAppointmentId(3);
        List<Appointment> appointments = List.of(appointment1, appointment2, appointment3);
        String responseBody = objectMapper.writeValueAsString(appointmentsDTO);
        //when
        when(appointmentService.findAllAppointmentsByPatientEmail(patientEmail)).thenReturn(appointments);
        when(appointmentMapper.map(appointment1)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withAppointmentCode(appointmentCode1)
                .withScheduleDateTime(OffsetDateTime.now(clock).minusHours(10))
                .withExecution(true));
        when(appointmentMapper.map(appointment2)).thenReturn(DtoFixtures.someAppointmentInformation1()
                        .withAppointmentCode(appointmentCode2)
                .withScheduleDateTime(OffsetDateTime.now(clock).minusHours(6))
                .withExecution(false));
        when(appointmentMapper.map(appointment3)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).plusHours(14))
                .withExecution(false));
        //then
        try(MockedStatic<OffsetDateTime> mockedStatic = mockStatic(OffsetDateTime.class)) {
            mockedStatic.when(() -> OffsetDateTime.now(ZoneOffset.UTC)).thenReturn(offsetDateTime);
            MvcResult result = mockMvc.perform(get(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.SHOW_NOT_ACTIVE_APPOINTMENTS)
                            .param("existingPatientEmail", patientEmail))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.appointments[*].appointmentCode", containsInAnyOrder(appointmentCode1, appointmentCode2)))
                           .andExpect(jsonPath("$.appointments.size()", is(2)))
                     .andReturn();
            assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
        }}

    @Test
    void deletingAppointmentWithRetrievingAppointmentInformationWorksCorrectly() throws Exception {
        //given
        String appointmentCode = "jkj45lk6-l9s8-fk34-j5al-al35l09d36dk";
        AppointmentsDTO appointmentsDTO = DtoFixtures.someAppointments3();
        String existingPatientEmail = "jakub_pacjent@gmail.com";
        String responseBody = objectMapper.writeValueAsString(appointmentsDTO);
        List<Appointment> appointmentList = List.of(
                DomainFixtures.someAppointment1(),
                DomainFixtures.someAppointment1(),
                DomainFixtures.someAppointment1());
        //when
        when(appointmentService.findAllAppointmentsByPatientEmail(existingPatientEmail)).thenReturn(appointmentList);
        when(appointmentMapper.map(Mockito.any(Appointment.class))).thenReturn(DtoFixtures.someAppointmentInformation1());
         //then
        MvcResult result = mockMvc.perform(delete(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.APPOINTMENT_DELETE, appointmentCode)
                        .param("existingPatientEmail", existingPatientEmail)
                        .queryParam("appointmentCode", appointmentCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointments.size()", is(3)))
                .andReturn();
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);

    }

    @Test
    void changingAppointmentExecutionCorrectly() throws Exception {
        //given
        String appointmentCode = "g3kj6828-05f5-fhj5-9da9-614109k881b5";
        Appointment appointment = DomainFixtures.someAppointment1();
        AppointmentInformationDTO appointmentInformationDTO = DtoFixtures.someAppointmentInformation1().withExecution(true);
        String responseBody = objectMapper.writeValueAsString(appointmentInformationDTO);
        //when
        when(appointmentService.changeAppointmentExecutionByAppointmentCode(appointmentCode)).thenReturn(appointment);
        when(appointmentMapper.map(appointment)).thenReturn(appointmentInformationDTO);
        //then
        MvcResult result = mockMvc.perform(patch(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.APPOINTMENT_CHANGE_EXECUTION, appointmentCode)
                        .queryParam("appointmentCode", appointmentCode))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.appointmentCode", is(appointmentInformationDTO.getAppointmentCode())))
                .andExpect(jsonPath("$.patientName", is(appointmentInformationDTO.getPatientName())))
                .andExpect(jsonPath("$.patientSurname", is(appointmentInformationDTO.getPatientSurname())))
                .andExpect(jsonPath("$.patientPesel", is(appointmentInformationDTO.getPatientPesel())))
                .andExpect(jsonPath("$.patientEmail", is(appointmentInformationDTO.getPatientEmail())))
                .andExpect(jsonPath("$.patientGender", is(appointmentInformationDTO.getPatientGender())))
                .andExpect(jsonPath("$.patientAge", is(appointmentInformationDTO.getPatientAge())))
                .andExpect(jsonPath("$.commentPatient", is(appointmentInformationDTO.getCommentPatient())))
                .andExpect(jsonPath("$.execution", is(appointmentInformationDTO.getExecution())))
                .andExpect(jsonPath("$.scheduleDateTime", is(appointmentInformationDTO.getScheduleDateTime())))
                .andExpect(jsonPath("$.medicalRecordCode", is(appointmentInformationDTO.getMedicalRecordCode())))
                .andExpect(jsonPath("$.commentDoctor", is(appointmentInformationDTO.getCommentDoctor())))
                .andExpect(jsonPath("$.diagnosis", is(appointmentInformationDTO.getDiagnosis())))
                .andReturn();
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);

    }

    @Test
    void thatEmailValidationWorksCorrectly() throws Exception {
        // given
        AppointmentDTO appointmentBody = DtoFixtures.someAppointment1().withPatientEmail("badEmail");
        String requestBody = objectMapper.writeValueAsString(appointmentBody);

        // when, then
        mockMvc.perform(post(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.MAKE_APPOINTMENT)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorId", notNullValue()));
    }

    @ParameterizedTest
    @MethodSource
    void thatPhoneValidationWorksCorrectly(Boolean correctPhone, String phone) throws Exception {
        // given
        AppointmentDTO appointmentBody = DtoFixtures.someAppointment1().withPatientPhone(phone);
        String requestBody = objectMapper.writeValueAsString(appointmentBody);

        // when, then
        if (correctPhone) {
            AppointmentDTO appointmentDTO = DtoFixtures.someAppointment2();
            String responseBody = objectMapper.writeValueAsString(appointmentDTO);
            when(appointmentMapper.mapToDTO(any())).thenReturn(appointmentDTO);
            MvcResult result = mockMvc.perform(post(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.MAKE_APPOINTMENT)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.appointmentCode", is(appointmentDTO.getAppointmentCode())))
                    .andExpect(jsonPath("$.commentPatient", is(appointmentDTO.getCommentPatient())))
                    .andExpect(jsonPath("$.execution", is(appointmentDTO.getExecution())))
                    .andReturn();
            assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
        } else {
            mockMvc.perform(post(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.MAKE_APPOINTMENT)
                            .content(requestBody)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.errorId", notNullValue()));
        }
    }

    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
        return Stream.of(
                Arguments.of(true, null),
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