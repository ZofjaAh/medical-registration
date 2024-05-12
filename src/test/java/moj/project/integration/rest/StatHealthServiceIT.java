package moj.project.integration.rest;

import moj.project.api.dto.StatBenefitDTO;
import moj.project.api.dto.StatSectionDTO;
import moj.project.integration.configuration.RestAssuredIntegrationTestBase;
import moj.project.integration.support.StatHealthServiceControllerTestSupport;
import moj.project.integration.support.WiremockTestSupport;
import moj.project.util.StatFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StatHealthServiceIT
extends RestAssuredIntegrationTestBase
implements StatHealthServiceControllerTestSupport, WiremockTestSupport {

    @Test
    void thatSearchingSectionsWorksCorrectly(){
        //given

        stubForSections(wireMockServer);
        //when
        List<StatSectionDTO> sections = getSections().getSections();
        //then
        Assertions.assertThat(sections.size()).isEqualTo(16);
        Assertions.assertThat(sections).containsAnyOf(StatFixtures.someSectionDTO1());
        Assertions.assertThat(sections).containsAnyOf(StatFixtures.someSectionDTO4());
    }
    @Test
    void thatSearchingBenefitsWorksCorrectly(){
        //given
        stubForBenefit1(wireMockServer);

        //when
        List<StatBenefitDTO> benefits1 = getMedicalBenefits("kwal", "1a","K - Choroby układu dokrewnego" , 1).getBenefits();
        //then
        Assertions.assertThat(benefits1.size()).isEqualTo(1);
        Assertions.assertThat(benefits1).containsAnyOf(StatFixtures.someBenefitDTO3());


    }
}

