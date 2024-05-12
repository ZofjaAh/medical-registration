package moj.project.api.dto.mapper;

import moj.project.api.dto.StatBenefitDTO;
import moj.project.api.dto.StatSectionDTO;
import moj.project.domain.StatBenefit;
import moj.project.domain.StatSection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatHealthServiceMapper {

    StatSectionDTO map(StatSection statSection);
    StatBenefitDTO map(StatBenefit benefits);
}
