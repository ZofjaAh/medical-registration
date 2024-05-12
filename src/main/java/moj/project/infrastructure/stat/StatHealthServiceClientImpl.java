package moj.project.infrastructure.stat;

import lombok.AllArgsConstructor;
import moj.project.businnes.dao.StatHealthServiceDAO;
import moj.project.domain.StatBenefit;
import moj.project.domain.StatSection;
import moj.project.domain.exception.NotFoundException;
import moj.project.infrastructure.stat.api.SownikiApi;
import moj.project.infrastructure.stat.model.BenefitsResponse;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StatHealthServiceClientImpl implements StatHealthServiceDAO {

    public static final String SECTION = "Choroby";
    public static final String FORMAT = "json";
    public static final String API_VERSION = "1.1";

    private final SownikiApi sownikiApi;
    private final StatHealthServiceMapper statHealthServiceMapper;


    @Override

    public List<StatSection> getSectionsJGP() {

        var section = Optional.ofNullable(sownikiApi.sekcjeSwiadczenJgp(SECTION, 1, 25, FORMAT, API_VERSION).block())
                .orElseThrow(() -> new NotFoundException(
                        "Could not find dictionary definition for: [%s]"
                                .formatted(SECTION)
                ));
         return Optional.ofNullable(section.getData())
                .map(data -> data.stream()
                        .map(sectionJGP ->
                                StatSection.builder()
                                        .description(sectionJGP)
                                        .build())
                        .toList())
                .orElseThrow(() -> new NotFoundException(
                        "Could not find any sectionJGP for: [%s]".formatted(SECTION)
                ));
    }
    @Override
    public List<StatBenefit> getBenefits(String benefit, String catalog, String section, int page) {

        var benefits = callBenefits(benefit, catalog, section, page);
        return benefits
                .map(BenefitsResponse::getData)
                .map(data -> data.stream()
                        .map(statHealthServiceMapper::map)
                        .toList())
                .orElse(Collections.emptyList());
    }
    private Optional<BenefitsResponse> callBenefits(String benefit, String catalog, String section, int page ) {
        return Optional.ofNullable(sownikiApi.swiadczeniaJgp(
                benefit,
                catalog,
                section,
                page,
                10,
                FORMAT,
                API_VERSION
        ).block());
    }
}
