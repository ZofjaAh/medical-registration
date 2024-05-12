package moj.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import moj.project.businnes.dao.MedicalRecordDAO;
import moj.project.domain.MedicalRecord;
import moj.project.infrastructure.database.entity.MedicalRecordEntity;
import moj.project.infrastructure.database.repository.jpa.MedicalRecordJpaRepository;
import moj.project.infrastructure.database.repository.mapper.MedicalRecordEntityMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class MedicalRecordRepository implements MedicalRecordDAO {
   private final MedicalRecordJpaRepository medicalRecordJpaRepository;
   private final MedicalRecordEntityMapper medicalRecordEntityMapper;
    @Override
    @Transactional
    public MedicalRecord saveMedicalRecord(MedicalRecord medicalRecord) {
        MedicalRecordEntity medicalRecordEntity = medicalRecordJpaRepository.saveAndFlush(medicalRecordEntityMapper.mapToEntity(medicalRecord));
        return medicalRecordEntityMapper.map(medicalRecordEntity);

    }
}
