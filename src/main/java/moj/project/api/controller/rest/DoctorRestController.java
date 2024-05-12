package moj.project.api.controller.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.businnes.DoctorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class DoctorRestController {
    public static final String API_DOCTOR_NEW= "/api/doctor/new";
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    @PostMapping(API_DOCTOR_NEW)
    public DoctorDTO addDoctor(
            @Valid @RequestBody DoctorDTO doctorDTO){
        return doctorMapper.map(doctorService.create(doctorMapper.map(doctorDTO)));


    }
}


