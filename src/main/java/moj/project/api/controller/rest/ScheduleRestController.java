package moj.project.api.controller.rest;

import lombok.AllArgsConstructor;
import moj.project.api.dto.DoctorScheduleDTO;
import moj.project.api.dto.DoctorSchedulesDTO;
import moj.project.api.dto.ScheduleDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.api.dto.mapper.ScheduleMapper;
import moj.project.businnes.DoctorService;
import moj.project.businnes.ScheduleService;
import moj.project.domain.Doctor;
import moj.project.domain.Schedule;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(ScheduleRestController.API_SCHEDULE)
public class ScheduleRestController {
    public static final String API_SCHEDULE = "/api/schedule";
    public static final String SCHEDULE_SHOW = "/show";
    public static final String SCHEDULE_ADD = "/add/{doctorCode}";
    public static final String SCHEDULE_UPDATE = "/update";
    public static final String SCHEDULE_DELETE = "/delete/{scheduleCode}";

    private final ScheduleService scheduleService;
    private final ScheduleMapper scheduleMapper;
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping(value = SCHEDULE_SHOW)
    public  DoctorScheduleDTO showSchedule(
            @RequestParam(value = "existingDoctorEmail", required = false)String existingDoctorEmail
            ){
       return doctorMapper.map(doctorService.findDoctorScheduleByDoctorEmail(existingDoctorEmail));
    }

    @PostMapping(value = SCHEDULE_ADD)
    public ScheduleDTO addSchedule(
            @PathVariable String doctorCode,
            @RequestBody ScheduleDTO scheduleDTO
            ){
        Schedule newSchedule = scheduleMapper.map(scheduleDTO);
        Doctor doctor = doctorService.findDoctor(doctorCode);
        Set< Schedule> schedules = new HashSet<>();
        if(Objects.isNull(doctor.getSchedules())){
        schedules.add(newSchedule);
        } else {
            schedules.addAll(doctor.getSchedules());
        schedules.add(newSchedule);
        }
        Doctor doctorToSave = doctor.withSchedules(schedules);
        return scheduleMapper.map(scheduleService.save(newSchedule.withDoctor(doctorToSave)));

    }
    @PutMapping(value = SCHEDULE_UPDATE)
    public ScheduleDTO updateSchedule(
            @RequestBody ScheduleDTO scheduleDTO){
        Schedule schedule = scheduleService.findScheduleByScheduleCode(scheduleDTO.getScheduleCode());
        Schedule scheduleToSave= schedule
                .withDateTime(scheduleMapper.mapStringToOffsetDateTime(scheduleDTO.getDate(),scheduleDTO.getTime()))
                .withDuration(scheduleDTO.getDuration());
        return  scheduleMapper.map(scheduleService.updateSchedule(scheduleToSave));

    }
    @DeleteMapping(value = SCHEDULE_DELETE)
    public DoctorSchedulesDTO deleteSchedule(
            @PathVariable(value = "scheduleCode") String scheduleCode,
            @RequestParam(value = "doctorEmail") String doctorEmail){
        scheduleService.deleteByScheduleCode(scheduleCode);


        return DoctorSchedulesDTO.builder()
                .schedules(getAvailableSchedules(doctorEmail))
                .build();

    }

        private List<DoctorScheduleDTO.ScheduleDTO> getAvailableSchedules(String existingDoctorEmail) {
      return doctorMapper.map(doctorService.findDoctorScheduleByDoctorEmail(existingDoctorEmail))
               .getSchedules().stream()
               .toList();
    }
}
