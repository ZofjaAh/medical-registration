package moj.project.infrastructure.database.repository.jpa;

import lombok.AllArgsConstructor;
import moj.project.infrastructure.database.entity.AppointmentEntity;
import moj.project.infrastructure.database.entity.PatientEntity;
import moj.project.util.EntityFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static moj.project.util.EntityFixtures.someAppointment0_1;
import static moj.project.util.EntityFixtures.somePatient1;


@AllArgsConstructor(onConstructor = @__(@Autowired))
class PatientJpaRepositoryTest extends AbstractJpa{
    private PatientJpaRepository patientJpaRepository;

@Test
    void thatPatientEntitySavedCorrectly(){
        //given
    PatientEntity patientEntity = somePatient1();
    String patientEmail = patientEntity.getEmail();
    //when
    patientJpaRepository.saveAndFlush(patientEntity);
    Optional<PatientEntity> patientSaved = patientJpaRepository.findByEmail(patientEmail);
    if(patientSaved.isPresent()){
        Assertions.assertEquals(patientSaved.get().getEmail(), patientEmail);}
    else
    Assertions.fail();
}
@Test
    void thatPatientHealthHistorySearchingWorksCorrectly(){
    //given
    PatientEntity patientEntity = EntityFixtures.somePatient0_1();
    String patientPesel = patientEntity.getPesel();
   String medicalRecordCode = someAppointment0_1().getMedicalRecord().getMedicalRecordCode();
    String doctorCode = someAppointment0_1().getSchedule().getDoctor().getDoctorCode();

    //when

    Optional<PatientEntity> healthPatientHistory = patientJpaRepository.findHealthPatientHistoryByPesel(patientPesel);

    if(healthPatientHistory.isPresent()){
        PatientEntity patient = healthPatientHistory.get();
        Set<AppointmentEntity> appointments = patient.getAppointments();
        List<String> medicalRecordCodes = appointments.stream()
                .filter(appointmentEntity -> Optional.ofNullable(appointmentEntity.getMedicalRecord()).isPresent())
                .map(appointmentEntity -> appointmentEntity.getMedicalRecord().getMedicalRecordCode())
                .toList();
        List<String> doctorCodes = appointments.stream().map(appointment -> appointment.getSchedule().getDoctor().getDoctorCode()).toList();
        //then
        Assertions.assertEquals(patient.getEmail(),patientEntity.getEmail());
        Assertions.assertTrue(medicalRecordCodes.contains(medicalRecordCode));
        Assertions.assertTrue(doctorCodes.contains(doctorCode));
}else Assertions.fail();

}


}