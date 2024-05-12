package moj.project.api.controller;

import lombok.AllArgsConstructor;
import moj.project.api.dto.DoctorScheduleDTO;
import moj.project.api.dto.ScheduleDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.api.dto.mapper.ScheduleMapper;
import moj.project.businnes.DoctorService;
import moj.project.businnes.ScheduleService;
import moj.project.domain.Doctor;
import moj.project.domain.Schedule;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    static final String SCHEDULE_SHOW = "/schedule/show";
    static final String SCHEDULE_ADD = "/schedule/add/{doctorCode}";
    static final String SCHEDULE_UPDATE = "/schedule/update";
    static final String SCHEDULE_DELETE = "/schedule/delete/{scheduleCode}";


    @GetMapping(SCHEDULE_SHOW)
    public String showDoctorSchedule(
            @RequestParam(value = "existingDoctorEmail")String existingDoctorEmail,
            ModelMap model){


        DoctorScheduleDTO doctorScheduleDTO = doctorMapper.map(doctorService.findDoctorScheduleByDoctorEmail(existingDoctorEmail));
        List<String> availableScheduleCodes = doctorScheduleDTO.getSchedules().stream().map(DoctorScheduleDTO.ScheduleDTO::getScheduleCode).toList();
        model.addAttribute("doctorScheduleDTO", doctorScheduleDTO);
        model.addAttribute("availableScheduleCodes", availableScheduleCodes);
        model.addAttribute("scheduleDTO", new ScheduleDTO());

        return "schedule";
    }

   @PostMapping(SCHEDULE_ADD)
   public String addSchedule(
           @PathVariable String doctorCode,
           @ModelAttribute("scheduleDTO") ScheduleDTO scheduleDTO,
           Model model
   ){
       Schedule newSchedule = scheduleMapper.map(scheduleDTO);
       Doctor doctor = doctorService.findDoctor(doctorCode);
       Set<Schedule> schedules = new HashSet<>(doctor.getSchedules());
       Doctor doctorToSave = doctor.withSchedules(schedules);

       ScheduleDTO schedule = scheduleMapper.map(scheduleService.save(newSchedule.withDoctor(doctorToSave)));

       model.addAttribute("scheduleDTO", schedule);
       model.addAttribute("existingDoctorEmail", doctor.getEmail());

       return "schedule_create";
   }
    @PutMapping(SCHEDULE_UPDATE)
    public String updateSchedule(
            @ModelAttribute("scheduleDTO") ScheduleDTO scheduleDTO,
            ModelMap model
    ) {
       Schedule schedule = scheduleService.findScheduleByScheduleCode(scheduleDTO.getScheduleCode());
        Schedule scheduleToSave= schedule
                .withDateTime(scheduleMapper.mapStringToOffsetDateTime(scheduleDTO.getDate(),scheduleDTO.getTime()))
                .withDuration(scheduleDTO.getDuration());
        ScheduleDTO savedSchedule = scheduleMapper.map(scheduleService.updateSchedule(scheduleToSave));

        model.addAttribute("scheduleDTO", savedSchedule);
        model.addAttribute("existingDoctorEmail", schedule.getDoctor().getEmail());

        return "schedule_update";
    }
    @RequestMapping(value = SCHEDULE_DELETE, method = {RequestMethod.GET, RequestMethod.DELETE})
    public ModelAndView deleteSchedule(
            @PathVariable String scheduleCode,
            @RequestParam(value = "doctorEmail") String doctorEmail,
           ModelMap model){
        scheduleService.deleteByScheduleCode(scheduleCode);
        String existingDoctorEmail = doctorEmail;

  model.addAttribute("existingDoctorEmail", existingDoctorEmail);
        return new ModelAndView("redirect:/schedule/show", model);
    }






}
