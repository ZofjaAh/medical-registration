package moj.project.businnes;

import lombok.AllArgsConstructor;
import moj.project.businnes.dao.DoctorDAO;
import moj.project.domain.Doctor;
import moj.project.domain.DoctorSchedule;
import moj.project.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DoctorService {
    private final DoctorDAO doctorDAO;
    @Transactional
    public List<Doctor> findAvailableDoctors() {
        return doctorDAO.findAvailable();
    }
@Transactional
    public Doctor findDoctor(String doctorCode) {
        Optional<Doctor> doctor = doctorDAO.findByCode(doctorCode);
        if (doctor.isEmpty()) {
            throw new NotFoundException("Could not find doctor by Code: [%s]".formatted(doctorCode));
        }
        return doctor.get();
    }
    @Transactional
    public DoctorSchedule findDoctorScheduleByDoctorEmail(String email) {
        Optional<DoctorSchedule> doctorSchedule = doctorDAO.findDoctorScheduleByDoctorEmail(email);
        if (doctorSchedule.isEmpty()) {
            throw new NotFoundException("Does not exist doctor's schedule  with email: [%s]".formatted(email));
        }
        return doctorSchedule.get();

    }
@Transactional
    public Doctor create(Doctor doctor) {

        return doctorDAO.create(doctor.withDoctorCode(UUID.randomUUID().toString()));
    }
}
