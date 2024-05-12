package moj.project.integration.rest;

import moj.project.api.dto.AppointmentDTO;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.AppointmentsDTO;
import moj.project.integration.configuration.RestAssuredIntegrationTestBase;
import moj.project.integration.support.AppointmentControllerTestSupport;
import moj.project.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AppointmentIT extends RestAssuredIntegrationTestBase
implements AppointmentControllerTestSupport {


    @Test
    void thatCreatingAppointmentWorksCorrectly(){
        //given
        AppointmentDTO appointmentDTO = DtoFixtures.someAppointment0_1();
        String patientEmail = appointmentDTO.getPatientEmail();
        //when
        AppointmentDTO appointmentSaved = makeAppointment(appointmentDTO);
        List<AppointmentInformationDTO> appointmentsAfterSaved = showActiveAppointments(patientEmail).getAppointments();
        //then
         assertThat(appointmentSaved)
                .usingRecursiveComparison()
                .ignoringFields("appointmentCode")
                        .isEqualTo(DtoFixtures.someAppointment0_2());
        assertThat(appointmentsAfterSaved.size()).isEqualTo(1);
     assertThat(appointmentsAfterSaved.get(0).getPatientEmail()).isEqualTo(patientEmail);
    }
    @Test
    void thatRetrievingAppointmentInformationWorksCorrectly(){
        //given
        AppointmentDTO appointmentDTO = DtoFixtures.someAppointment0_1();
        AppointmentDTO appointmentSaved = makeAppointment(appointmentDTO);
        String appointmentCode = appointmentSaved.getAppointmentCode();
        //when
        assertThat(appointmentCode).isNotNull();
        AppointmentInformationDTO result = showAppointmentInformation(appointmentCode);
        //then
        assertThat(result).usingRecursiveComparison()
                .ignoringFields("appointmentCode")
                .isEqualTo(DtoFixtures.someAppointmentInformation0_1());
    }
    @Test
    void thatChangingAppointmentExecutionWorksCorrectly(){
        //given
        AppointmentDTO appointmentDTO = DtoFixtures.someAppointment0_1();
        AppointmentDTO appointmentSaved = makeAppointment(appointmentDTO);
        String appointmentCode = appointmentSaved.getAppointmentCode();
        //when
        AppointmentInformationDTO result = changeAppointmentExecution(appointmentCode);
        //then
        assertThat(appointmentSaved.getExecution()).isFalse();
        assertThat(result.getExecution()).isTrue();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("appointmentCode")
                .isEqualTo(DtoFixtures.someAppointmentInformation0_1().withExecution(true));
    }
    @Test
    void thatDeletingAppointmentWorksCorrectly(){
        //given
        AppointmentDTO appointmentDTO = DtoFixtures.someAppointment0_3();
        String patientEmail = appointmentDTO.getExistingPatientEmail();
        AppointmentDTO appointmentSaved = makeAppointment(appointmentDTO);
       String appointmentCode = appointmentSaved.getAppointmentCode();
        //when
        AppointmentsDTO appointmentsBeforeDeleting = showActiveAppointments(patientEmail);
        deleteAppointments(appointmentCode,patientEmail);
        AppointmentsDTO appointmentsAfterDeleting = showActiveAppointments(patientEmail);
        //then
        assertThat(appointmentsBeforeDeleting.getAppointments().size()).isEqualTo(2);
        assertThat(appointmentsAfterDeleting.getAppointments().size()).isEqualTo(1);
       assertThat(appointmentsAfterDeleting.getAppointments().get(0).getAppointmentCode()).isNotEqualTo(appointmentCode);
    }
    @Test
    void thatSearchingAppointmentsWorksCorrectly(){
        //given //when
        AppointmentDTO appointmentDTO = DtoFixtures.someAppointment0_3();
        String patientEmail = appointmentDTO.getExistingPatientEmail();

        List<AppointmentInformationDTO> activeAppointmentsBeforeAdding = showActiveAppointments(patientEmail).getAppointments();

        AppointmentDTO appointmentSaved = makeAppointment(appointmentDTO);
        String appointmentCode = appointmentSaved.getAppointmentCode();

        List<AppointmentInformationDTO> activeAppointmentsAfterAdding = showActiveAppointments(patientEmail).getAppointments();
        List<AppointmentInformationDTO> notActiveAppointmentsBeforeChanging = showNotActiveAppointments(patientEmail).getAppointments();

        changeAppointmentExecution(appointmentCode);

        List<AppointmentInformationDTO> notActiveAppointmentsAfterChanging = showNotActiveAppointments(patientEmail).getAppointments();
        List<AppointmentInformationDTO> activeAppointmentsAfterChanging = showActiveAppointments(patientEmail).getAppointments();

        //then
        assertThat(activeAppointmentsBeforeAdding.size()).isEqualTo(1);
        assertThat(activeAppointmentsAfterAdding.size()).isEqualTo(2);
        assertThat(notActiveAppointmentsBeforeChanging.size()).isEqualTo(1);
        assertThat(notActiveAppointmentsAfterChanging.size()).isEqualTo(2);
        assertThat(activeAppointmentsAfterChanging.size()).isEqualTo(1);

        activeAppointmentsAfterAdding.removeAll(activeAppointmentsAfterChanging);
        assertThat(activeAppointmentsAfterAdding.get(0).getAppointmentCode()).isEqualTo(appointmentCode);
    }

}
