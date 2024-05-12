package moj.project.businnes.dao;

import moj.project.domain.Doctor;
import moj.project.domain.DoctorSchedule;

import java.util.List;
import java.util.Optional;

public interface DoctorDAO {
    List<Doctor> findAvailable();

    Optional<Doctor> findByCode(String doctorCode);
  Optional < DoctorSchedule> findDoctorScheduleByDoctorEmail(String email);

    Doctor create(Doctor doctor);
}
