package moj.project.api.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import moj.project.api.dto.StatBenefitsDTO;
import moj.project.api.dto.StatSectionsDTO;
import moj.project.api.dto.mapper.StatHealthServiceMapper;
import moj.project.businnes.StatHealthServiceService;
import moj.project.domain.StatBenefit;
import moj.project.domain.StatSection;
import moj.project.util.StatFixtures;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatHealthServiceRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
class StatHealthServiceRestControllerWebMvcTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @MockBean
    private final StatHealthServiceMapper statHealthServiceMapper;
    @MockBean
    private final StatHealthServiceService statHealthServiceService;

    @Test
    void thatRetrievingMedicalBenefitsWorksCorrectly() throws Exception {
        //given
        String benefit = "lecz";
        String catalog = "1a";
        String section = "J -";
        Integer page = 1;
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("benefit", benefit);
        params.add("catalog", catalog);
        params.add("section", section);
        params.add("page", page.toString());
        StatBenefitsDTO statBenefitsDTO = StatFixtures.someBenefitsDTO1();
        String responseBody = objectMapper.writeValueAsString(statBenefitsDTO);
        StatBenefit statBenefit1 = StatFixtures.someBenefit1();
        StatBenefit statBenefit2 = StatFixtures.someBenefit2();
        //when
        when(statHealthServiceService.getBenefits(benefit, catalog, section, page))
                .thenReturn(List.of(statBenefit1, statBenefit2));
        when(statHealthServiceMapper.map(statBenefit1)).thenReturn(StatFixtures.someBenefitDTO1());
        when(statHealthServiceMapper.map(StatFixtures.someBenefit2())).thenReturn(StatFixtures.someBenefitDTO2());
        //then
        MvcResult result = mockMvc.perform(get(StatHealthServiceRestController.API_STAT
                        + StatHealthServiceRestController.SHOW_BENEFITS)
                        .params(params))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.benefits[0].name", containsString(statBenefitsDTO.getBenefits().get(0).getName())))
                .andExpect(jsonPath("$.benefits[1].name", containsString(statBenefitsDTO.getBenefits().get(1).getName())))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(responseBody);

    }

    @Test
    void thatRetrievingSectionsWorksCorrectly() throws Exception {
        //given
        StatSectionsDTO statSectionsDTO = StatFixtures.someSectionsDTO1();
        String responseBody = objectMapper.writeValueAsString(statSectionsDTO);
        StatSection statSection1 = StatFixtures.someSection1();
        StatSection statSection2 = StatFixtures.someSection2();
        StatSection statSection3 = StatFixtures.someSection3();

        //when
        when(statHealthServiceService.getSections()).thenReturn(List.of(
                statSection1
               ,statSection2
                ,statSection3
                ));
        when(statHealthServiceMapper.map(statSection1)).thenReturn(StatFixtures.someSectionDTO1());
        when(statHealthServiceMapper.map(statSection2)).thenReturn(StatFixtures.someSectionDTO2());
        when(statHealthServiceMapper.map(statSection3)).thenReturn(StatFixtures.someSectionDTO3());
        //then
        MvcResult result = mockMvc.perform(get(StatHealthServiceRestController.API_STAT
                        + StatHealthServiceRestController.SHOW_SECTIONS))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sections[0].description", containsString(statSectionsDTO.getSections().get(0).getDescription())))
                .andExpect(jsonPath("$.sections[1].description", containsString(statSectionsDTO.getSections().get(1).getDescription())))
                .andExpect(jsonPath("$.sections[2].description", containsString(statSectionsDTO.getSections().get(2).getDescription())))
                .andReturn();
        assertThat(result.getResponse().getContentAsString(StandardCharsets.UTF_8))
                .isEqualTo(responseBody);

    }


}