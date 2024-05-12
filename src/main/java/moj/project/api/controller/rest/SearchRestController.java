package moj.project.api.controller.rest;

import lombok.AllArgsConstructor;
import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.DoctorsDTO;
import moj.project.api.dto.ScheduleDTO;
import moj.project.api.dto.SchedulesDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.api.dto.mapper.ScheduleMapper;
import moj.project.businnes.DoctorService;
import moj.project.businnes.ScheduleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(SearchRestController.API_SEARCH)
public class SearchRestController {
    public static final String API_SEARCH = "/api/search";
    public static final String DOCTORS = "/doctors";
    public static final String SHOW_TIME_SLOTS = "/{doctorCode}";

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;

    @GetMapping(DOCTORS)
    public DoctorsDTO showDoctors() {
        return DoctorsDTO.builder().doctors(getAvailableDoctors()).build();

    }

    @GetMapping(SHOW_TIME_SLOTS)
    public SchedulesDTO showAvailableTimeSlots(
            @PathVariable String doctorCode) {
        return SchedulesDTO.builder()
                .schedules(getAvailableSchedules(doctorCode)).build();

    }

    private List<ScheduleDTO> getAvailableSchedules(String doctorCode) {
        return scheduleService.findAvailableByDoctorCode(doctorCode)
                .stream()
                .map(scheduleMapper::map)
                .toList();
    }

    private List<DoctorDTO> getAvailableDoctors() {
        return doctorService.findAvailableDoctors()
                .stream()
                .map(doctorMapper::map)
                .toList();
    }
}


