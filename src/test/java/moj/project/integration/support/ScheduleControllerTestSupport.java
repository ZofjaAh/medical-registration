package moj.project.integration.support;

import io.restassured.specification.RequestSpecification;
import moj.project.api.controller.rest.ScheduleRestController;
import moj.project.api.dto.DoctorScheduleDTO;
import moj.project.api.dto.DoctorSchedulesDTO;
import moj.project.api.dto.ScheduleDTO;
import org.springframework.http.HttpStatus;

public interface ScheduleControllerTestSupport {
    RequestSpecification requestSpecification();
    default DoctorScheduleDTO showScheduleTimeSlots(final String existingDoctorEmail) {

        return requestSpecification()
                .param("existingDoctorEmail", existingDoctorEmail)
                .get(ScheduleRestController.API_SCHEDULE + ScheduleRestController.SCHEDULE_SHOW)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(DoctorScheduleDTO.class);
    }
    default void showScheduleTimeSlotsWithException(final String notExistingDoctorEmail) {

        requestSpecification()
                .param("existingDoctorEmail", notExistingDoctorEmail)
                .get(ScheduleRestController.API_SCHEDULE + ScheduleRestController.SCHEDULE_SHOW)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value())
                ;
    }
    default ScheduleDTO addScheduleTimeSlot(final String doctorCode,final ScheduleDTO scheduleDTO) {

        return requestSpecification()
                .pathParam("doctorCode", doctorCode)
                .body(scheduleDTO)
                .post(ScheduleRestController.API_SCHEDULE + ScheduleRestController.SCHEDULE_ADD)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(ScheduleDTO.class);
    }
    default ScheduleDTO updateScheduleTimeSlot(final ScheduleDTO scheduleDTO) {

        return requestSpecification()
                .body(scheduleDTO)
                .put(ScheduleRestController.API_SCHEDULE + ScheduleRestController.SCHEDULE_UPDATE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(ScheduleDTO.class);
    }
    default DoctorSchedulesDTO deleteScheduleTimeSlot(final String scheduleCode, final String doctorEmail) {

        return requestSpecification()
                .pathParam("scheduleCode", scheduleCode)
                .param("doctorEmail", doctorEmail)
                .delete(ScheduleRestController.API_SCHEDULE + ScheduleRestController.SCHEDULE_DELETE)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(DoctorSchedulesDTO.class);
    }


}
