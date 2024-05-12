package moj.project.infrastructure.database.repository.mapper;

import moj.project.domain.MedicalRecord;
import moj.project.infrastructure.database.entity.MedicalRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface MedicalRecordEntityMapper {

    MedicalRecordEntity mapToEntity(MedicalRecord medicalRecord);

    MedicalRecord map(MedicalRecordEntity medicalRecordEntity);


}
