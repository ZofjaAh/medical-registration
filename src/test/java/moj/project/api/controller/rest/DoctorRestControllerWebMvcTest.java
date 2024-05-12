package moj.project.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.businnes.DoctorService;
import moj.project.domain.Doctor;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DoctorRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorRestControllerWebMvcTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private DoctorMapper doctorMapper;
    @Test
    void creatingDoctorWorksCorrectly() throws Exception {
        //given
        String doctorCode = "i8j5hs90-j593-547d-djg4-lk84db7987k6";
        String email = "borys_zawalny88@gmail.pl";
        DoctorDTO doctorBody = DtoFixtures.someDoctor2().withEmail(email);
        DoctorDTO doctorDTO = DtoFixtures.someDoctor2().withExistingDoctorEmail(email);
        String requestBody = objectMapper.writeValueAsString(doctorBody);
        String responseBody = objectMapper.writeValueAsString(doctorDTO);

        //when
        when(doctorMapper.map(any(DoctorDTO.class))).thenReturn(DomainFixtures.someDoctor1());
        when(doctorService.create(any(Doctor.class))).thenReturn(DomainFixtures.someDoctor1().withDoctorCode(doctorCode));
        when(doctorMapper.map(any(Doctor.class))).thenReturn(doctorDTO);

        //then
        MvcResult result = mockMvc.perform(post(DoctorRestController.API_DOCTOR_NEW)
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.existingDoctorEmail", is(doctorDTO.getExistingDoctorEmail())))
                .andExpect(jsonPath("$.doctorCode", is(doctorDTO.getDoctorCode())))
                .andExpect(jsonPath("$.name", is(doctorDTO.getName())))
                .andExpect(jsonPath("$.surname", is(doctorDTO.getSurname())))
                .andExpect(jsonPath("$.pesel", is(doctorDTO.getPesel())))
                .andExpect(jsonPath("$.specialityCode", is(doctorDTO.getSpecialityCode())))
                .andExpect(jsonPath("$.specialityDefinition", is(doctorDTO.getSpecialityDefinition())))
                .andExpect(jsonPath("$.addressCountry", is(doctorDTO.getAddressCountry())))
                .andExpect(jsonPath("$.addressCity", is(doctorDTO.getAddressCity())))
                .andExpect(jsonPath("$.addressPostalCode", is(doctorDTO.getAddressPostalCode())))
                .andExpect(jsonPath("$.addressStreet", is(doctorDTO.getAddressStreet())))
                .andReturn();
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(responseBody);
    }
    @Test
    void thatEmailValidationWorksCorrectly() throws Exception {
        // given
        DoctorDTO doctorBody = DtoFixtures.someDoctor2().withEmail("badEmail");
        String requestBody = objectMapper.writeValueAsString(doctorBody);

        // when, then
        mockMvc.perform(post(DoctorRestController.API_DOCTOR_NEW)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorId", notNullValue()));
    }





}