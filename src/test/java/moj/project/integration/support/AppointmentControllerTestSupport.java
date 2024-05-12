package moj.project.integration.support;

import io.restassured.specification.RequestSpecification;
import moj.project.api.controller.rest.AppointmentRestController;
import moj.project.api.dto.AppointmentDTO;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.AppointmentsDTO;
import moj.project.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;

public interface AppointmentControllerTestSupport {
    RequestSpecification requestSpecification();
    default AppointmentInformationDTO showAppointmentInformation(final String appointmentCode) {

        return requestSpecification()
                .pathParam("appointmentCode", appointmentCode)
                .get(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.SHOW_APPOINTMENT_INFORMATION)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AppointmentInformationDTO.class);
    }
    default AppointmentDTO makeAppointment(final AppointmentDTO appointmentDTO) {

        return requestSpecification()
                .body( appointmentDTO)
                .post(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.MAKE_APPOINTMENT)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AppointmentDTO.class);
    }
    default AppointmentsDTO showActiveAppointments(final String existingPatientEmail) {

        return requestSpecification()
                .param("existingPatientEmail", existingPatientEmail)
                .get(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.SHOW_ACTIVE_APPOINTMENTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AppointmentsDTO.class);
    }
    default String showActiveAppointmentsWithNotExistingEmail(final String notExistingPatientEmail) {

        return requestSpecification()
                .param("existingPatientEmail", notExistingPatientEmail)
                .get(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.SHOW_ACTIVE_APPOINTMENTS)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .and()
                .extract().body()
                .as(NotFoundException.class).getMessage();
    }
    default AppointmentsDTO showNotActiveAppointments(final String existingPatientEmail) {

        return requestSpecification()
                .param("existingPatientEmail", existingPatientEmail)
                .get(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.SHOW_NOT_ACTIVE_APPOINTMENTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AppointmentsDTO.class);
    }
    default AppointmentsDTO deleteAppointments(final String appointmentCode, final String existingPatientEmail) {

        return requestSpecification()
                .pathParam("appointmentCode", appointmentCode)
                .param("existingPatientEmail", existingPatientEmail)
                .delete(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.APPOINTMENT_DELETE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AppointmentsDTO.class);
    }
    default AppointmentInformationDTO changeAppointmentExecution(final String appointmentCode) {

        return requestSpecification()
                .pathParam("appointmentCode", appointmentCode)
                .patch(AppointmentRestController.API_APPOINTMENT + AppointmentRestController.APPOINTMENT_CHANGE_EXECUTION)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AppointmentInformationDTO.class);
    }


}
