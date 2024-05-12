package moj.project.integration.support;

import io.restassured.specification.RequestSpecification;
import moj.project.api.controller.rest.SearchRestController;
import moj.project.api.dto.DoctorsDTO;
import moj.project.api.dto.SchedulesDTO;
import org.springframework.http.HttpStatus;

public interface SearchControllerTestSupport {
    RequestSpecification requestSpecification();
    default DoctorsDTO showDoctors() {

        return requestSpecification()
                .get(SearchRestController.API_SEARCH + SearchRestController.DOCTORS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(DoctorsDTO.class);
    }
    default SchedulesDTO showAvailableTimeSlots(final String doctorCode) {

        return requestSpecification()
                .pathParam("doctorCode", doctorCode)
                .get(SearchRestController.API_SEARCH + SearchRestController.SHOW_TIME_SLOTS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(SchedulesDTO.class);
    }


}
