package moj.project.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import moj.project.api.dto.HealthPatientHistoryDTO;
import moj.project.api.dto.mapper.PatientMapper;
import moj.project.businnes.PatientService;
import moj.project.util.DtoFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PatientHistoryRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class PatientHistoryRestControllerWebMvcTest {
   private MockMvc mockMvc;
   private ObjectMapper objectMapper;
    @MockBean
    private PatientService patientService;
    @MockBean
    private PatientMapper patientMapper;
    @Test
    void retrievingHealthPatientsHistoryWorksCorrectly() throws Exception{
        //given
        String  patientPesel = "98012132434";
        HealthPatientHistoryDTO healthPatientHistoryDTO = DtoFixtures.someHealthPatientHistory1();
        String responseBody = objectMapper.writeValueAsString(healthPatientHistoryDTO);
        //when
        when(patientMapper.map(Mockito.any())).thenReturn(DtoFixtures.someHealthPatientHistory1());
        //then
        MvcResult result = mockMvc.perform(get(PatientHistoryRestController.API_PATIENT_HISTORY, patientPesel)
                .queryParam("patientPesel", patientPesel))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.patientPesel", is(healthPatientHistoryDTO.getPatientPesel())))
                .andExpect(jsonPath("$.appointments.size()", is(1)))
                .andExpect(jsonPath("$.appointments[0].appointmentCode",
                        is(healthPatientHistoryDTO.getAppointments().get(0).getAppointmentCode())))
                .andReturn();
        Assertions.assertThat(result.getResponse().getContentAsString()).isEqualTo(responseBody);
    }


}