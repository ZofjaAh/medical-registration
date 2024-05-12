package moj.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import moj.project.businnes.dao.PatientDAO;
import moj.project.domain.HealthPatientHistory;
import moj.project.domain.Patient;
import moj.project.infrastructure.database.entity.PatientEntity;
import moj.project.infrastructure.database.repository.jpa.PatientJpaRepository;
import moj.project.infrastructure.database.repository.jpa.UserJpaRepository;
import moj.project.infrastructure.database.repository.mapper.PatientEntityMapper;
import moj.project.infrastructure.security.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepository implements PatientDAO {
    private final PatientJpaRepository patientJpaRepository;
    private final PatientEntityMapper patientEntityMapper;
    private final UserJpaRepository userEntityJpaRepository;
    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientJpaRepository.findByEmail(email)
                .map(patientEntityMapper::mapFromEntity);
    }

    @Override
    public Patient savePatient(Patient patient) {
        PatientEntity patientToSave = patientEntityMapper.mapToEntity(patient);
        UserEntity userEntity = userEntityJpaRepository.findByEmail(patient.getEmail());

        PatientEntity patientEntity = patientJpaRepository.saveAndFlush(patientToSave
                .withUser(userEntity));
        return patientEntityMapper.mapFromEntity(patientEntity);
    }

    @Override
    public Optional<HealthPatientHistory> findHealthPatientHistoryByPatientPesel(String patientPesel) {
        return patientJpaRepository.findHealthPatientHistoryByPesel(patientPesel)
                .map(patientHistory-> patientEntityMapper.mapFromEntity(patientPesel, patientHistory));

    }
}
