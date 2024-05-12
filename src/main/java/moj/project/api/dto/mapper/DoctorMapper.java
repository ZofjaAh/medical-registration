package moj.project.api.dto.mapper;

import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.DoctorScheduleDTO;
import moj.project.domain.Address;
import moj.project.domain.Doctor;
import moj.project.domain.DoctorSchedule;
import moj.project.domain.Speciality;
import org.mapstruct.Mapper;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface DoctorMapper extends OffsetDateTimeMapper {
    default DoctorDTO map(Doctor doctor) {
        return DoctorDTO.builder()
                .existingDoctorEmail(doctor.getEmail())
                .doctorCode(doctor.getDoctorCode())
                .name(doctor.getName())
                .surname(doctor.getSurname())
                .pesel(doctor.getPesel())
                .specialityCode(doctor.getSpeciality().getSpecialityCode())
                .specialityDefinition(doctor.getSpeciality().getDefinition())
                .addressCountry(doctor.getAddress().getCountry())
                .addressCity(doctor.getAddress().getCity())
                .addressPostalCode(doctor.getAddress().getPostalCode())
                .addressStreet(doctor.getAddress().getStreetAddress())
                .build();
    }

    default Doctor map(DoctorDTO doctorDTO) {
        return Doctor.builder()

                .name(doctorDTO.getName())
                .surname(doctorDTO.getSurname())
                .pesel(doctorDTO.getPesel())
                .email(Objects.isNull(doctorDTO.getExistingDoctorEmail())? doctorDTO.getEmail()
                        : doctorDTO.getExistingDoctorEmail())
                .speciality(Speciality.builder()
                        .specialityCode(doctorDTO.getSpecialityCode())
                        .definition(doctorDTO.getSpecialityDefinition())
                        .build())
                .address(Address.builder()
                        .country(doctorDTO.getAddressCountry())
                        .city(doctorDTO.getAddressCity())
                        .postalCode(doctorDTO.getAddressPostalCode())
                        .streetAddress(doctorDTO.getAddressStreet())
                        .build())
                .build();
    }

    default DoctorScheduleDTO map(DoctorSchedule doctorSchedule) {
        return DoctorScheduleDTO.builder()
                .doctorNameSurname(doctorSchedule.getDoctorNameSurname())
                .doctorCode(doctorSchedule.getDoctorCode())
                .doctorEmail(doctorSchedule.getDoctorEmail())
                .schedules(doctorSchedule.getSchedules().stream()
                        .map(schedule -> DoctorScheduleDTO.ScheduleDTO.builder()
                                .scheduleCode(schedule.getScheduleCode())
                                .dateTime(mapOffsetDateTimeToString(schedule.getDateTime()))
                                .duration(schedule.getDuration())
                                .availability(schedule.getAvailability())
                                .appointmentCode(schedule.getAppointment().getAppointmentCode())
                                .execution(schedule.getAppointment().getExecution())
                                .medicalRecordCode(schedule.getMedicalRecord().getMedicalRecordCode())
                                .build())
                        .toList())
                .build();
    }

}
