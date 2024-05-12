package moj.project.infrastructure.database.repository;

import lombok.AllArgsConstructor;
import moj.project.businnes.dao.DoctorDAO;
import moj.project.domain.Doctor;
import moj.project.domain.DoctorSchedule;
import moj.project.infrastructure.database.entity.DoctorEntity;
import moj.project.infrastructure.database.repository.jpa.DoctorJpaRepository;
import moj.project.infrastructure.database.repository.jpa.UserJpaRepository;
import moj.project.infrastructure.database.repository.mapper.DoctorEntityMapper;
import moj.project.infrastructure.security.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class DoctorRepository implements DoctorDAO {
    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorEntityMapper doctorEntityMapper;
    private final UserJpaRepository userJpaRepository;
    @Override
    public List<Doctor> findAvailable() {
        return  doctorJpaRepository.findAvailable().stream()
                .map(doctorEntityMapper::mapFromEntity)
                .toList();

    }
    @Override
    public Optional<DoctorSchedule> findDoctorScheduleByDoctorEmail(String email) {
        return doctorJpaRepository.findDoctorScheduleByEmail(email)
                .map(doctorEntityMapper::mapFromDoctorEntity);
    }

    @Override
    public Doctor create(Doctor doctor) {
        DoctorEntity doctorToSave = doctorEntityMapper.mapToEntity(doctor);
        UserEntity userEntity = userJpaRepository.findByEmail(doctor.getEmail());

        DoctorEntity doctorEntity = doctorJpaRepository.saveAndFlush(doctorToSave
                .withUser(userEntity));
        return doctorEntityMapper.mapFromEntity(doctorEntity);
    }

    @Override
    public Optional<Doctor> findByCode(String doctorCode) {
        return doctorJpaRepository.findByDoctorCode(doctorCode)
                .map(doctorEntityMapper::mapFromEntity);
    }
}
