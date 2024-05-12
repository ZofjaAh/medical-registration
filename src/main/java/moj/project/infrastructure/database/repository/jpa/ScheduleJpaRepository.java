package moj.project.infrastructure.database.repository.jpa;

import moj.project.infrastructure.database.entity.ScheduleEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleJpaRepository extends JpaRepository<ScheduleEntity, Integer> {
    @Query("""
    SELECT sch FROM ScheduleEntity sch
    LEFT JOIN FETCH sch.doctor doctor
    WHERE doctor.doctorCode = :doctorCode
    AND sch.availability = true
    """)
    List<ScheduleEntity> findAvailableByDoctorCode(String doctorCode);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "appointment",
                    "doctor"
            })
    Optional<ScheduleEntity> findByScheduleCode(String scheduleCode);

    @Query("""
    SELECT sch FROM ScheduleEntity sch
    INNER JOIN FETCH AppointmentEntity app
    ON app.schedule.scheduleId = sch.scheduleId
    WHERE app.appointmentCode = :appointmentCode
    """)
   Optional<ScheduleEntity> findByAppointmentCode(String appointmentCode);

    void deleteByScheduleCode(String scheduleCode);
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "appointment",
                    "doctor"
            })
    List<ScheduleEntity> findAll();
}
