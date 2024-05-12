package moj.project.api.dto;

import lombok.*;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    private String addressCity;
    private String speciality;
}
