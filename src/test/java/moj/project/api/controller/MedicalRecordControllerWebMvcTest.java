package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.AppointmentInformationDTO;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class MedicalRecordControllerWebMvcTest {
    private MockMvc mockMvc;
    @MockBean
    private MedicalRecordService medicalRecordService;
    @MockBean
    private AppointmentService appointmentService;
    @MockBean
    private AppointmentMapper appointmentMapper;

    @Test
    void thatCreatingMedicalRecordWorksCorrectlyWithRetrievingAppointmentInformation() throws Exception {
        //given
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> parametersMap = DtoFixtures.someMedicalRecord1().asMap();;
                parametersMap.forEach(parameters::add);

        String appointmentCode = "76015b28-f5f5-4b6d-9da9-6141d61881b5";
        AppointmentInformationDTO appointmentInformationDTO = DtoFixtures.someAppointmentInformation1_1();
         //when
        when(appointmentService.findAppointmentInformationByAppointmentCode(appointmentCode))
                .thenReturn(DomainFixtures.someAppointment1_1());
        when(appointmentMapper.map(any(Appointment.class))).thenReturn(appointmentInformationDTO);
        //then
        mockMvc.perform(post(MedicalRecordController.ADD_MEDICAL_RECORD, appointmentCode)
                        .queryParam("appointmentCode", appointmentCode)
                        .params(parameters))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/appointment/doctor/" + appointmentCode))
                .andExpect(status().isFound());


    }
}