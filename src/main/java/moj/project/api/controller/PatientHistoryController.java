package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.HealthPatientHistoryDTO;
import moj.project.api.dto.mapper.PatientMapper;
import moj.project.businnes.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class PatientHistoryController {
    static final String PATIENT_HISTORY = "/patient-history/{patientPesel}";

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping(PATIENT_HISTORY)
    public String showPatientHealthHistory(
            @PathVariable String patientPesel,
            @RequestParam(value = "appointmentCode") String appointmentCode,
            Model model){
        HealthPatientHistoryDTO healthPatientHistoryDTO = patientMapper.map(patientService.findHealthPatientHistoryByPatientPesel(patientPesel));

                model.addAttribute("healthPatientHistoryDTO", healthPatientHistoryDTO);
                model.addAttribute("appointmentCode", appointmentCode);
        return "patient_history";
    }




}
