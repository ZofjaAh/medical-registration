package moj.project.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.MedicalRecordDTO;
import moj.project.api.dto.mapper.AppointmentMapper;
import moj.project.businnes.AppointmentService;
import moj.project.businnes.MedicalRecordService;
import moj.project.domain.Appointment;
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

import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MedicalRecordRestControllerWebMvcTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockBean
    private MedicalRecordService medicalRecordService;
    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private AppointmentMapper appointmentMapper;

    @Test
    void thatCreatingMedicalRecordWorksCorrectlyWithRetrievingAppointmentInformation() throws Exception {
        //given
        MedicalRecordDTO medicalRecordDTO = DtoFixtures.someMedicalRecord1();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ssXXXXX");
        String appointmentCode = "76015b28-f5f5-4b6d-9da9-6141d61881b5";
        AppointmentInformationDTO appointmentInformationDTO = DtoFixtures.someAppointmentInformation1_1();
        String requestBody = objectMapper.writeValueAsString(medicalRecordDTO);
        String responseBody = objectMapper.writeValueAsString(appointmentInformationDTO);
        //when
        when(appointmentService.findAppointmentInformationByAppointmentCode(appointmentCode))
                .thenReturn(DomainFixtures.someAppointment1_1());
        when(appointmentMapper.map(any(Appointment.class))).thenReturn(appointmentInformationDTO);
        //then
        MvcResult result = mockMvc.perform(post(MedicalRecordRestController.API_RECORD + MedicalRecordRestController.ADD_MEDICAL_RECORD, appointmentCode)
                        .queryParam("appointmentCode", appointmentCode)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
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
}