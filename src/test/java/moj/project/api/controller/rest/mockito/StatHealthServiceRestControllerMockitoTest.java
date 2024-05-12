package moj.project.api.controller.rest.mockito;

import moj.project.api.controller.rest.StatHealthServiceRestController;
import moj.project.api.dto.StatBenefitDTO;
import moj.project.api.dto.StatBenefitsDTO;
import moj.project.api.dto.StatSectionDTO;
import moj.project.api.dto.StatSectionsDTO;
import moj.project.api.dto.mapper.StatHealthServiceMapper;
import moj.project.businnes.StatHealthServiceService;
import moj.project.domain.StatBenefit;
import moj.project.domain.StatSection;
import moj.project.util.StatFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatHealthServiceRestControllerMockitoTest {
    @Mock
    private StatHealthServiceMapper statHealthServiceMapper;
    @Mock
    private StatHealthServiceService statHealthServiceService;
    @InjectMocks
    private StatHealthServiceRestController statHealthServiceRestController;
    @Test
    void thatRetrievingMedicalBenefitsWorksCorrectly(){
        //given
        String benefit = "lecz";
        String catalog = "1a";
        String section = "J -";
        Integer page = 1;
        List<StatBenefitDTO> availableBenefits = List.of(StatFixtures.someBenefitDTO1()
                , StatFixtures.someBenefitDTO2());
        StatBenefit statBenefit1 = StatFixtures.someBenefit1();
        StatBenefit statBenefit2 = StatFixtures.someBenefit2();
        //when
        when(statHealthServiceService.getBenefits(benefit, catalog, section, page))
                .thenReturn(List.of(statBenefit1, statBenefit2));
        when(statHealthServiceMapper.map(statBenefit1)).thenReturn(StatFixtures.someBenefitDTO1());
        when(statHealthServiceMapper.map(statBenefit2)).thenReturn(StatFixtures.someBenefitDTO2());
        //then
        StatBenefitsDTO result = statHealthServiceRestController.getMedicalBenefits(benefit,catalog,section,page);
        Assertions.assertThat(result.getBenefits().size()).isEqualTo(availableBenefits.size());
        Assertions.assertThat(result.getBenefits().stream().sorted().toList()).isEqualTo(availableBenefits.stream().sorted().toList());
    }
    @Test
    void thatRetrievingSectionsWorksCorrectly(){
        //given
        List<StatSectionDTO> availableSections = List.of(StatFixtures.someSectionDTO1(),
                StatFixtures.someSectionDTO2(),
                StatFixtures.someSectionDTO3());
        StatSection statSection1 = StatFixtures.someSection1();
        StatSection statSection2 = StatFixtures.someSection2();
        StatSection statSection3 = StatFixtures.someSection3();

        //when
        when(statHealthServiceService.getSections()).thenReturn(List.of(statSection1, statSection2, statSection3));
        when(statHealthServiceMapper.map(statSection1)).thenReturn(StatFixtures.someSectionDTO1());
        when(statHealthServiceMapper.map(statSection2)).thenReturn(StatFixtures.someSectionDTO2());
        when(statHealthServiceMapper.map(statSection3)).thenReturn(StatFixtures.someSectionDTO3());
        //then
        StatSectionsDTO result = statHealthServiceRestController.getSections();
        Assertions.assertThat(result.getSections().size()).isEqualTo(availableSections.size());

    }


}