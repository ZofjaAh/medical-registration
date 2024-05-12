package moj.project.api.controller.rest.mockito;

import moj.project.api.controller.rest.MedicalRecordRestController;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.MedicalRecordDTO;
import moj.project.api.dto.mapper.AppointmentMapper;
import moj.project.businnes.AppointmentService;
import moj.project.businnes.MedicalRecordService;
import moj.project.domain.Appointment;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRecordRestControllerMockitoTest {
    @Mock
    private MedicalRecordService medicalRecordService;
    @Mock
    private AppointmentService appointmentService;
    @Mock
    private AppointmentMapper appointmentMapper;
    @InjectMocks
   private MedicalRecordRestController medicalRecordRestController;
    @Test
    void thatCreatingMedicalRecordWorksCorrectlyWithRetrievingAppointmentInformation(){
        //given
        String appointmentCode = "76015b28-f5f5-4b6d-9da9-6141d61881b5";
        MedicalRecordDTO medicalRecordDTO = DtoFixtures.someMedicalRecord1();
        //when
        when(appointmentService.findAppointmentByAppointmentCode(appointmentCode)).thenReturn(DomainFixtures.someAppointment1());
        when(appointmentService.findAppointmentInformationByAppointmentCode(appointmentCode)).thenReturn(
                DomainFixtures.someAppointment1_1());
        when(appointmentMapper.map(any(Appointment.class))).thenReturn(DtoFixtures.someAppointmentInformation1_1());
        //then
        AppointmentInformationDTO result = medicalRecordRestController.addMedicalRecord(appointmentCode, medicalRecordDTO);
        Assertions.assertThat(result).isEqualTo(DtoFixtures.someAppointmentInformation1_1());
    }
}