package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.AppointmentDTO;
import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.ScheduleDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.api.dto.mapper.ScheduleMapper;
import moj.project.businnes.DoctorService;
import moj.project.businnes.ScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@AllArgsConstructor
public class SearchController {
    public static final String SEARCH = "/search";
    public static final String SEARCH_TIME_SLOTS = "/search/{doctorCode}";

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
   private final ScheduleService scheduleService;
   private final ScheduleMapper scheduleMapper;
    @GetMapping(SEARCH)
    public String showDoctors(Model model){
       List<DoctorDTO> availableDoctorDTOs = doctorService.findAvailableDoctors()
                .stream()
                .map(doctorMapper::map)
                .toList();
        model.addAttribute("availableDoctorDTOs", availableDoctorDTOs);

        return "search";
    }
    @GetMapping(SEARCH_TIME_SLOTS)
    public String showAvailableTimeSlots(
            @PathVariable String doctorCode,
            Model model){
        DoctorDTO doctorDTO = doctorMapper.map(doctorService.findDoctor(doctorCode));


        List<ScheduleDTO> availableScheduleDTOs = scheduleService.findAvailableByDoctorCode(doctorCode).stream()
              .map(scheduleMapper::map)
              .toList();
        List<String> scheduleCodes = availableScheduleDTOs.stream().map(ScheduleDTO::getScheduleCode).toList();


        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("availableScheduleDTOs", availableScheduleDTOs);
        model.addAttribute("scheduleCodes", scheduleCodes);
        model.addAttribute("appointmentDTO", new AppointmentDTO());

        return "time_slot";
    }

}
