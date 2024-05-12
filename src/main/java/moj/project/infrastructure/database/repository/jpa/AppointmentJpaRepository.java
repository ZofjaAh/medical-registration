package moj.project.infrastructure.database.repository.jpa;

import moj.project.infrastructure.database.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentJpaRepository extends JpaRepository<AppointmentEntity, Integer> {
    @Override
    <S extends AppointmentEntity> S saveAndFlush(S entity);

    void deleteByAppointmentCode(String appointmentCode);
   @Query("""
   SELECT app FROM AppointmentEntity app
    LEFT JOIN FETCH app.patient patient
    WHERE patient.email = :email
    """)
    List<AppointmentEntity> findAllByPatientEmail(String email);


    @EntityGraph(
            type = EntityGraph.EntityGraphType.LOAD,
            attributePaths = {
                    "medicalRecord",
                    "schedule",
                    "schedule.doctor",
                    "patient"
            })
    AppointmentEntity findAppointmentInformationByAppointmentCode(String appointmentCode);


    AppointmentEntity findAppointmentByAppointmentCode(String appointmentCode);
}
