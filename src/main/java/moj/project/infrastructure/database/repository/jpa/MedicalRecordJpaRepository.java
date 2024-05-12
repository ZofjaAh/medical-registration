package moj.project.infrastructure.database.repository.jpa;

import moj.project.infrastructure.database.entity.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordJpaRepository extends JpaRepository<MedicalRecordEntity, Integer> {
}
