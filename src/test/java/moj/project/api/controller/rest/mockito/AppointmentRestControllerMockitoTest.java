package moj.project.api.controller.rest.mockito;

import moj.project.api.controller.rest.AppointmentRestController;
import moj.project.api.dto.AppointmentDTO;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.AppointmentsDTO;
import moj.project.api.dto.mapper.AppointmentMapper;
import moj.project.businnes.AppointmentService;
import moj.project.domain.Appointment;
import moj.project.domain.AppointmentRequest;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentRestControllerMockitoTest {
    @Mock
    private AppointmentService appointmentService;
    @Mock
    private AppointmentMapper appointmentMapper;
    @InjectMocks
    private AppointmentRestController appointmentRestController;
     @Test
    void thatRetrievingAppointmentInformationWorksCorrectly(){
         //given
         String appointmentCode = "76015b28-f5f5-4b6d-9da9-6141d61881b5";
         Appointment appointment = DomainFixtures.someAppointment1();
         //when
         when(appointmentService.findAppointmentInformationByAppointmentCode(appointmentCode)).thenReturn(appointment);
         when(appointmentMapper.map(appointment)).thenReturn(DtoFixtures.someAppointmentInformation1());
         AppointmentInformationDTO result = appointmentRestController.showAppointmentInformation(appointmentCode);
         //then
         Assertions.assertThat(result).isEqualTo(DtoFixtures.someAppointmentInformation1());

     }
    @Test
    void thatCreatingAppointmentWorksCorrectly(){
        //given
        AppointmentDTO appointmentDTO = DtoFixtures.someAppointment1();
        AppointmentRequest appointmentRequest = DomainFixtures.someAppointmentRequest1();
        Appointment appointment = DomainFixtures.someAppointment2();
        //when
        when(appointmentMapper.map(appointmentDTO)).thenReturn(appointmentRequest);

        when(appointmentService.createFirstTimeBook(appointmentRequest)).thenReturn(appointment);
        when(appointmentMapper.mapToDTO(appointment)).thenReturn(DtoFixtures.someAppointment2());
        AppointmentDTO result = appointmentRestController.makeAppointment(appointmentDTO);
        //then
        Assertions.assertThat(result).isEqualTo(DtoFixtures.someAppointment2());

    }
    @Test
    void thatShowAllActiveAppointmentsCorrectly(){
         //given
        String patientEmail = "giorg_niezdrowy@gmail.com";
        Clock clock = Clock.fixed(Instant.parse("2024-08-22T10:00:01Z"), ZoneOffset.UTC);
        OffsetDateTime offsetDateTime = OffsetDateTime.now(clock);
        Appointment appointment1 = DomainFixtures.someAppointment1().withAppointmentId(1);
        Appointment appointment2 = DomainFixtures.someAppointment1().withAppointmentId(2);
        Appointment appointment3 = DomainFixtures.someAppointment1().withAppointmentId(3);

        List<Appointment> appointments = List.of(appointment1, appointment2, appointment3);

        //when
        when(appointmentService.findAllAppointmentsByPatientEmail(patientEmail)).thenReturn( appointments);
        when(appointmentMapper.map(appointment1)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).minusHours(8))
                .withExecution(false));
        when(appointmentMapper.map(appointment2)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).minusHours(6))
                .withExecution(true));
        when(appointmentMapper.map(appointment3)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).plusHours(3))
                .withExecution(false));
        //then
        try(MockedStatic<OffsetDateTime> mockedStatic = mockStatic(OffsetDateTime.class)) {
            mockedStatic.when(() -> OffsetDateTime.now(ZoneOffset.UTC)).thenReturn(offsetDateTime);
            AppointmentsDTO result = appointmentRestController.showActiveAppointments(patientEmail);
            Assertions.assertThat(result.getAppointments().size()).isEqualTo(1);
        }}
 @Test
    void thatShowAllNotActiveAppointmentsCorrectly(){
         //given
        String patientEmail = "giorg_niezdrowy@gmail.com";
     Clock clock = Clock.fixed(Instant.parse("2024-08-22T10:00:01Z"), ZoneOffset.UTC);
     OffsetDateTime offsetDateTime = OffsetDateTime.now(clock);
     Appointment appointment1 = DomainFixtures.someAppointment1().withAppointmentId(1);
     Appointment appointment2 = DomainFixtures.someAppointment1().withAppointmentId(2);
     Appointment appointment3 = DomainFixtures.someAppointment1().withAppointmentId(3);

     List<Appointment> appointments = List.of(appointment1, appointment2, appointment3);
     //when
        when(appointmentService.findAllAppointmentsByPatientEmail(patientEmail)).thenReturn( appointments);
        when(appointmentMapper.map(appointment1)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).minusHours(10))
                .withExecution(true));
        when(appointmentMapper.map(appointment2)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).minusHours(6))
                .withExecution(false));
        when(appointmentMapper.map(appointment3)).thenReturn(DtoFixtures.someAppointmentInformation1()
                .withScheduleDateTime(OffsetDateTime.now(clock).plusHours(14))
                .withExecution(false));
        //then
     try(MockedStatic<OffsetDateTime> mockedStatic = mockStatic(OffsetDateTime.class)) {
         mockedStatic.when(() -> OffsetDateTime.now(ZoneOffset.UTC)).thenReturn(offsetDateTime);
         AppointmentsDTO result = appointmentRestController.showNotActiveAppointments(patientEmail);
        Assertions.assertThat(result.getAppointments().size()).isEqualTo(2);
     }
    }
    @Test
    void thatDeletingAppointmentWithRetrievingAppointmentInformationWorksCorrectly(){
         //given
        String appointmentCode = "jkj45lk6-l9s8-fk34-j5al-al35l09d36dk";

        String existingPatientEmail = "jakub_pacjent@gmail.com";
        List<Appointment> appointmentList = List.of(
                DomainFixtures.someAppointment1(),
                DomainFixtures.someAppointment1(),
                DomainFixtures.someAppointment1());
        //when
                when(appointmentService.findAllAppointmentsByPatientEmail(existingPatientEmail)).thenReturn(appointmentList);
      when(appointmentMapper.map(Mockito.any(Appointment.class))).thenReturn(DtoFixtures.someAppointmentInformation1());
      //then
        AppointmentsDTO result = appointmentRestController.deleteAppointment(appointmentCode, existingPatientEmail);
    Assertions.assertThat(result.getAppointments().size()).isEqualTo(appointmentList.size());
     }
    @Test
    void thatChangingAppointmentExecutionCorrectly(){
         //given
        String appointmentCode = "g3kj6828-05f5-fhj5-9da9-614109k881b5";
        Appointment appointment = DomainFixtures.someAppointment1();
        //when
        when(appointmentService.changeAppointmentExecutionByAppointmentCode(appointmentCode)).thenReturn(appointment);
        when(appointmentMapper.map(appointment)).thenReturn(DtoFixtures.someAppointmentInformation1().withExecution(true));
    //then
        AppointmentInformationDTO result = appointmentRestController.changeAppointmentExecution(appointmentCode);
        Assertions.assertThat(result.getExecution()).isEqualTo(true);
    }

}