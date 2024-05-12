package moj.project.util;

import lombok.experimental.UtilityClass;
import moj.project.api.dto.StatBenefitDTO;
import moj.project.api.dto.StatBenefitsDTO;
import moj.project.api.dto.StatSectionDTO;
import moj.project.api.dto.StatSectionsDTO;
import moj.project.domain.StatBenefit;
import moj.project.domain.StatSection;

import java.util.List;

@UtilityClass
public class StatFixtures {
    public static StatBenefit someBenefit1() {
        return StatBenefit.builder()
                .code("5.51.01.0009034")
                .name("J34 LECZENIE CHIRURGICZNE ZMIAN TROFICZNYCH STOPY *").build();
    }
    public static StatBenefit someBenefit2() {
        return StatBenefit.builder()
                .code("5.51.01.0009035")
                .name("J35 LECZENIE ANOMALII NACZYNIOWYCH LASEREM PULSACYJNO-BARWNIKOWYM<18 R.Ż.").build();
    }


    public static StatBenefitDTO someBenefitDTO1() {
        return StatBenefitDTO.builder()
                .code("5.51.01.0009034")
                .name("J34 LECZENIE CHIRURGICZNE ZMIAN TROFICZNYCH STOPY *").build();
    }
    public static StatBenefitDTO someBenefitDTO2() {
        return StatBenefitDTO.builder()
                .code("5.51.01.0009035")
                .name("J35 LECZENIE ANOMALII NACZYNIOWYCH LASEREM PULSACYJNO-BARWNIKOWYM<18 R.Ż.").build();
    }
    public static StatBenefitDTO someBenefitDTO3() {
        return StatBenefitDTO.builder()
                .code("5.51.01.0010063")
                .name("K63 KWALIFIKACJA DO LECZENIA JODEM RADIOAKTYWNYM RAKA TARCZYCY LUB OCENA SKUTECZNOŚCI LECZENIA Z ZASTOSOWANIEM REKOMBINOWANEGO TSH [RHTSH]").build();
    }
    public static StatBenefitDTO someBenefitDTO4() {
        return StatBenefitDTO.builder()
                .code("5.52.01.0000811")
                .name("GRUŹLICA - LECZENIE DŁUGOTERMINOWE").build();
    }
    public static StatBenefitDTO someBenefitDTO5() {
        return StatBenefitDTO.builder()
                .code("5.51.01.0011086")
                .name("L86 BADANIA W ZAKRESIE DRÓG MOCZOWYCH").build();
    }

    public static StatSection someSection1() {
        return StatSection.builder()
                .description("A - Choroby układu nerwowego")
                .build();
    }
    public static StatSection someSection2() {
        return StatSection.builder()
                .description("B - Choroby oczu")
                .build();
    }
    public static StatSection someSection3() {
        return StatSection.builder()
                .description("C - Choroby twarzy, jamy ustnej, gardła, krtani, nosa i uszu")
                .build();
    }

    public static StatSectionDTO someSectionDTO1() {
        return StatSectionDTO.builder()
                .description("A - Choroby układu nerwowego")
                .build();
    }
    public static StatSectionDTO someSectionDTO2() {
        return StatSectionDTO.builder()
                .description("B - Choroby oczu")
                .build();
    }
    public static StatSectionDTO someSectionDTO3() {
        return StatSectionDTO.builder()
                .description("C - Choroby twarzy, jamy ustnej, gardła, krtani, nosa i uszu")
                .build();
    }
    public static StatSectionDTO someSectionDTO4() {
        return StatSectionDTO.builder()
                .description("PZ - Choroby dziecięce - leczenie zabiegowe")
                .build();
    }

    public static StatBenefitsDTO someBenefitsDTO1() {
        return StatBenefitsDTO.builder()
                .benefits(List.of(someBenefitDTO1(),
                        someBenefitDTO2())).build();
    }

    public static StatSectionsDTO someSectionsDTO1() {
        return StatSectionsDTO.builder()
                .sections(List.of(someSectionDTO1(),
                        someSectionDTO2(),
                        someSectionDTO3()))
                .build();
    }
}
