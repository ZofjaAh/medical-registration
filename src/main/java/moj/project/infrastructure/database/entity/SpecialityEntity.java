package moj.project.infrastructure.database.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(of = "specialityId")
@ToString(of = {"specialityId", "specialityCode", "definition"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "speciality")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "specialityId")
public class SpecialityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "speciality_id")
    private Integer specialityId;

    @Column(name = "speciality_code")
    private String specialityCode;

    @Column(name = "definition")
    private String definition;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "speciality")
    private Set<DoctorEntity> doctors;
}
