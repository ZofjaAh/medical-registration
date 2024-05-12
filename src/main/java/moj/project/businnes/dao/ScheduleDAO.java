package moj.project.businnes.dao;

import moj.project.domain.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleDAO {
    List<Schedule> findAvailableByDoctorCode(String doctorCode);

    Optional<Schedule> findByScheduleCode(String scheduleCode);


    void changeAvailability(String appointmentCode);


    void deleteByScheduleCode(String scheduleCode);

    Schedule save(Schedule schedule);

    Schedule updateSchedule(Schedule schedule);


    List<Schedule> findAllSchedules();
}
