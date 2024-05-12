package moj.project.infrastructure.security;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import moj.project.infrastructure.database.entity.DoctorEntity;
import moj.project.infrastructure.database.entity.PatientEntity;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "medical_registration_user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "user_id")
    private int id;
    @Column(name= "user_name")
    private String userName;
    @Column(name= "email")
    private String email;
    @Column(name= "password")
    private String password;
    @Column(name= "active")
    private Boolean active;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "medical_registration_user_role",
            joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private PatientEntity patient;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user")
    private DoctorEntity doctor;
}
