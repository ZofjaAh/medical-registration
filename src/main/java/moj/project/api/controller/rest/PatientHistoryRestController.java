package moj.project.api.controller.rest;

import lombok.AllArgsConstructor;
import moj.project.api.dto.HealthPatientHistoryDTO;
import moj.project.api.dto.mapper.PatientMapper;
import moj.project.businnes.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class PatientHistoryRestController {
    public static final String API_PATIENT_HISTORY= "/api/patient/history/{patientPesel}";
    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping(API_PATIENT_HISTORY)
    public HealthPatientHistoryDTO showPatientHealthHistory(
            @PathVariable String patientPesel){
       return patientMapper.map(patientService.findHealthPatientHistoryByPatientPesel(patientPesel));



    }
}


