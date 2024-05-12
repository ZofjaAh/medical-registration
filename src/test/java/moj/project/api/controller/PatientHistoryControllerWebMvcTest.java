package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.HealthPatientHistoryDTO;
import moj.project.api.dto.mapper.PatientMapper;
import moj.project.businnes.PatientService;
import moj.project.util.DtoFixtures;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PatientHistoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class PatientHistoryControllerWebMvcTest {
   private MockMvc mockMvc;
    @MockBean
    private PatientService patientService;
    @MockBean
    private PatientMapper patientMapper;
    @Test
    void retrievingHealthPatientsHistoryWorksCorrectly() throws Exception{
        //given
        String  patientPesel = "98012132434";
        String appointmentCode = "76015b28-f5f5-4b6d-9da9-6141d61881b5";
        HealthPatientHistoryDTO healthPatientHistoryDTO = DtoFixtures.someHealthPatientHistory1();
        //when
        when(patientMapper.map(Mockito.any())).thenReturn(healthPatientHistoryDTO);
        //then
        mockMvc.perform(get(PatientHistoryController.PATIENT_HISTORY, patientPesel)
                .queryParam("patientPesel", patientPesel)
                        .param("appointmentCode", appointmentCode))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("healthPatientHistoryDTO"))
                .andExpect(model().attributeExists("appointmentCode"))
                .andExpect(view().name("patient_history"));

                }


}