package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.businnes.DoctorService;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DoctorController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class DoctorControllerWebMvcTest {
    private MockMvc mockMvc;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private DoctorMapper doctorMapper;
    @Test
    void doctorWorksCorrectly() throws Exception {
        // given, when, then
        mockMvc.perform(get(DoctorController.DOCTOR))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("doctorDTO"))
                .andExpect(view().name("doctor"));
    }

    @Test
    void creatingDoctorWorksCorrectly() throws Exception {
        //given
        String doctorCode = "i8j5hs90-j593-547d-djg4-lk84db7987k6";
        String email = "borys_zawalny88@gmail.pl";
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> parametersMap =  DtoFixtures.someDoctor2().withEmail(email).asMap();
        parametersMap.forEach(parameters::add);

        //when
        when(doctorService.create(any())).thenReturn(DomainFixtures.someDoctor1().withDoctorCode(doctorCode));

        //then
       mockMvc.perform(post(DoctorController.DOCTOR_NEW)
                       .params(parameters))
               .andExpect(status().isOk())
               .andExpect(model().attributeExists("doctorName"))
               .andExpect(model().attributeExists("doctorSurname"))
               .andExpect(model().attributeExists("doctorCode"))
               .andExpect(view().name("doctor_registration"));

    }
    @Test
    void emailValidationWorksCorrectly() throws Exception {
        // given
        String badEmail = "badEmail";
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String, String> parametersMap =  DtoFixtures.someDoctor2().withEmail(badEmail).asMap();
        parametersMap.forEach(parameters::add);

        // when, then
        mockMvc.perform(post(DoctorController.DOCTOR_NEW)
                        .params(parameters))
                .andExpect(status().isBadRequest())
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage",Matchers.containsString(badEmail)))
                .andExpect(view().name("error")); }








}