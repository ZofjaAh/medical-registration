package moj.project.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.businnes.DoctorService;
import moj.project.domain.Doctor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class DoctorController {
    static final String DOCTOR= "/doctor";
    static final String DOCTOR_NEW= "/doctor/new";
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping(DOCTOR)
    public String doctorPage(ModelMap model){

        model.addAttribute("doctorDTO", new DoctorDTO());
        return "doctor";
    }

    @PostMapping(DOCTOR_NEW)
    public String addDoctor(
            @Valid @ModelAttribute("doctorDTO") DoctorDTO doctorDTO,
            ModelMap model){
       Doctor doctor = doctorService.create(doctorMapper.map(doctorDTO));

            model.addAttribute("doctorName", doctorDTO.getName());
            model.addAttribute("doctorSurname", doctorDTO.getSurname());
               model.addAttribute("doctorCode", doctor.getDoctorCode());

        return "doctor_registration";
    }


}
