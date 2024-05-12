package moj.project.businnes.dao;

import moj.project.domain.HealthPatientHistory;
import moj.project.domain.Patient;

import java.util.Optional;

public interface PatientDAO {
    Optional<Patient> findByEmail(String email);

    Patient savePatient(Patient patient);

    Optional<HealthPatientHistory> findHealthPatientHistoryByPatientPesel(String patientPesel);
}

