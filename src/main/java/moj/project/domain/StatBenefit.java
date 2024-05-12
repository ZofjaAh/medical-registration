package moj.project.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StatBenefit {
    String code;
    String name;
}
