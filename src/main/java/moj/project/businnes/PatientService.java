package moj.project.businnes;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import moj.project.businnes.dao.PatientDAO;
import moj.project.domain.HealthPatientHistory;
import moj.project.domain.Patient;
import moj.project.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Slf4j
@Service
@AllArgsConstructor
public class PatientService {
    private final PatientDAO patientDAO;
    @Transactional
    public Patient findPatient(String existingPatientEmail) {
        Optional<Patient> patient = patientDAO.findByEmail(existingPatientEmail);
        if (patient.isEmpty()) {
            log.error("Patient with email [{}] dose not exist", existingPatientEmail);
            throw new NotFoundException("Could not find patient by email: [%s]".formatted(existingPatientEmail));
        }
        return patient.get();
    }
@Transactional
    public Patient savePatient (Patient patient) {

        return patientDAO.savePatient(patient);
    }
@Transactional
    public HealthPatientHistory findHealthPatientHistoryByPatientPesel(String patientPesel) {
        Optional<HealthPatientHistory> patientHistory = patientDAO.findHealthPatientHistoryByPatientPesel(patientPesel);
        if (patientHistory.isEmpty()) {
            throw new NotFoundException("Could not find patientHistory by email: [%s]".formatted(patientPesel));
        }
        return patientHistory.get();

    }
}
