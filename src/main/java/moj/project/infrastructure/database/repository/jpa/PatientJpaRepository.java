package moj.project.infrastructure.database.repository.jpa;

import moj.project.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientEntity, Integer> {
    Optional<PatientEntity> findByEmail(String email);
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "appointments",
                    "appointments.medicalRecord",
                    "appointments.schedule",
                    "appointments.schedule.doctor"
            })
    Optional<PatientEntity> findHealthPatientHistoryByPesel(String pesel);
}

