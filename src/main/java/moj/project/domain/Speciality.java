package moj.project.domain;

import lombok.*;

import java.util.Set;

@Value
@With
@Builder
@EqualsAndHashCode(of = "specialityId")
@ToString(of = {"specialityId", "specialityCode", "definition"})
public class Speciality {
    Integer specialityId;
    String specialityCode;
    String definition;
    Set<Doctor> doctors;
}
