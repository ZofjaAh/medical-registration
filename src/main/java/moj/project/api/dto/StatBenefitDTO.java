package moj.project.api.dto;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatBenefitDTO implements Comparable<StatBenefitDTO>{
    private String code;
    private String name;


    @Override
    public int compareTo(StatBenefitDTO o) {
        return this.code.compareTo(o.code);
    }
}
