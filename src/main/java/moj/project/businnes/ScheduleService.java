package moj.project.businnes;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import moj.project.businnes.dao.ScheduleDAO;
import moj.project.domain.DateTimePeriod;
import moj.project.domain.Schedule;
import moj.project.domain.exception.NotFoundException;
import moj.project.domain.exception.ProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ScheduleService {

    private final ScheduleDAO scheduleDAO;
@Transactional
    public List<Schedule> findAvailableByDoctorCode(String doctorCode) {

        return scheduleDAO.findAvailableByDoctorCode(doctorCode);
    }
@Transactional
    public Schedule findScheduleByScheduleCode(String scheduleCode) {
        if(Objects.nonNull(scheduleCode)){
        Optional<Schedule> schedule = scheduleDAO.findByScheduleCode(scheduleCode);
        if (schedule.isEmpty()) {
            throw new NotFoundException("Could not find time slot by code: [%s]".formatted(scheduleCode));
        }
        return schedule.get();
    }else throw new NullPointerException("Could not find time slot, the code is null");
    }
@Transactional
    public void changeAvailability(String appointmentCode) {
        scheduleDAO.changeAvailability(appointmentCode);

    }

@Transactional
    public void deleteByScheduleCode(String scheduleCode) {
        if (isAvailable(scheduleCode)) {
            scheduleDAO.deleteByScheduleCode(scheduleCode);
        } else throw new ProcessingException(
                "Delete is impossible, Time slot with code: [%s] is reserved by patient  ".formatted(scheduleCode));

    }
@Transactional
    public Schedule save(Schedule schedule) {
        String scheduleCode = schedule.getScheduleCode();
        if (isTimeSlotEmpty(schedule)) {
          return scheduleDAO.save(schedule);
        } else throw new ProcessingException(
                "Saving Time slot with code: [%s] is  is impossible, the DateTime is reserved ".formatted(scheduleCode));


    }
@Transactional
    public Schedule updateSchedule(Schedule schedule) {
        String scheduleCode = schedule.getScheduleCode();
        if (isTimeSlotEmpty(schedule) && isAvailable(scheduleCode)) {
           return scheduleDAO.updateSchedule(schedule);
        } else throw new ProcessingException(
                "Update is impossible, Time slot with code: [%s] is reserved ".formatted(scheduleCode));

    }

    private Boolean isAvailable(String scheduleCode) {
        Schedule schedule = scheduleDAO.findByScheduleCode(scheduleCode)
                .orElseThrow(() -> new EntityNotFoundException(
                        "ScheduleEntity not found, scheduleCode: [%s]".formatted(scheduleCode)
                ));
        Boolean availability = schedule.getAvailability();

        return (Objects.isNull(schedule.getAppointment()) && availability.equals(true));

    }

    private Boolean isTimeSlotEmpty(Schedule schedule) {
        List<Schedule> allSchedules = scheduleDAO.findAllSchedules();
        List<DateTimePeriod> dateTimePeriodList = allSchedules.stream()
                .filter(timeSlot -> timeSlot.getDoctor().getDoctorId().equals(schedule.getDoctor().getDoctorId()))
                .filter(timeSlot -> !timeSlot.getScheduleCode().equals(schedule.getScheduleCode()))
                .map(timeSlot -> new DateTimePeriod(timeSlot.getDateTime(),
                        timeSlot.getDateTime().plusMinutes(timeSlot.getDuration() - 1)))
                .toList();

        OffsetDateTime startDateTime = schedule.getDateTime();
        OffsetDateTime endDateTime = schedule.getDateTime().plusMinutes(schedule.getDuration() - 1);
        return dateTimePeriodList.stream()
                .noneMatch((period) -> !isTimeNotReserved(period.getStartTimeSlot(), period.getEndTimeSlot(), startDateTime, endDateTime));
    }

    private boolean isTimeNotReserved(OffsetDateTime startTimeSlot,
                                   OffsetDateTime endTimeSlot,
                                   OffsetDateTime startDateTime,
                                   OffsetDateTime endDateTime) {
        //true
        return startDateTime.isBefore(startTimeSlot) && endDateTime.isBefore(startTimeSlot)
                || startDateTime.isAfter(endTimeSlot);

    }



}

