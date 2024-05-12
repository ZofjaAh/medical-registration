package moj.project.infrastructure.stat;

import moj.project.domain.StatBenefit;
import moj.project.infrastructure.stat.model.Benefit;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component
public class StatHealthServiceMapper {
    public StatBenefit map(Benefit data) {
        var builder = StatBenefit.builder();
        Optional.ofNullable(data)
                .ifPresent(dto-> builder.code(dto.getCode())
                        .name(dto.getName())
                );
        return builder.build();
    }



}
