package moj.project.infrastructure.database.repository.jpa;

import moj.project.infrastructure.database.entity.DoctorEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Integer> {
@Query("""
    SELECT doc FROM DoctorEntity doc
    INNER JOIN FETCH ScheduleEntity sch
    ON sch.doctor.doctorId = doc.doctorId
    WHERE sch.availability = true
    """)
Set<DoctorEntity> findAvailable();

    Optional<DoctorEntity> findByDoctorCode(String doctorCode);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "schedules",
                    "schedules.appointment",
                    "schedules.appointment.medicalRecord"
            }

    )
   Optional<DoctorEntity> findDoctorScheduleByEmail(String email);
}
