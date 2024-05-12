package moj.project.infrastructure.database.repository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import moj.project.businnes.dao.ScheduleDAO;
import moj.project.domain.Schedule;
import moj.project.infrastructure.database.entity.ScheduleEntity;
import moj.project.infrastructure.database.repository.jpa.ScheduleJpaRepository;
import moj.project.infrastructure.database.repository.mapper.ScheduleEntityMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class ScheduleRepository implements ScheduleDAO {
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final ScheduleEntityMapper scheduleEntityMapper;

    @Override
    public List<Schedule> findAvailableByDoctorCode(String doctorCode) {
        return scheduleJpaRepository.findAvailableByDoctorCode(doctorCode).stream()
                .map(scheduleEntityMapper::mapFromEntity)
                .toList();
    }

    @Override
    public Optional<Schedule> findByScheduleCode(String scheduleCode) {
        return scheduleJpaRepository.findByScheduleCode(scheduleCode)
                .map(scheduleEntityMapper::mapFromEntity);

    }

    @Override
    public void changeAvailability(String appointmentCode) {
        ScheduleEntity schedule = scheduleJpaRepository.findByAppointmentCode(appointmentCode)
                .orElseThrow();
        scheduleJpaRepository.saveAndFlush(schedule.withAvailability(true));
    }

    @Override
    public void deleteByScheduleCode(String scheduleCode) {
         scheduleJpaRepository.deleteByScheduleCode(scheduleCode);

    }


    @Override
    public Schedule save(Schedule schedule) {
        return scheduleEntityMapper.mapFromEntity(scheduleJpaRepository
                .saveAndFlush(scheduleEntityMapper.map(schedule)));

    }

    @Override
    public Schedule updateSchedule(Schedule schedule) {
        ScheduleEntity scheduleEntity = scheduleJpaRepository.findByScheduleCode(schedule.getScheduleCode())
                .orElseThrow(() -> new EntityNotFoundException(
                        "ScheduleEntity not found, scheduleCode: [%s]".formatted(schedule.getScheduleCode())
                ));
        scheduleEntity.setDateTime(schedule.getDateTime());
        scheduleEntity.setDuration(schedule.getDuration());
      return  scheduleEntityMapper.mapFromEntity( scheduleJpaRepository.saveAndFlush(scheduleEntity));
    }

    @Override
    public List<Schedule> findAllSchedules() {

        return scheduleJpaRepository.findAll()
                .stream().map(scheduleEntityMapper::mapFromEntity)
                .toList();
    }

}
