package moj.project.integration.support;

import io.restassured.specification.RequestSpecification;
import moj.project.api.controller.rest.DoctorRestController;
import moj.project.api.dto.DoctorDTO;
import org.springframework.http.HttpStatus;

public interface DoctorControllerTestSupport {
    RequestSpecification requestSpecification();
    default DoctorDTO addDoctor(final DoctorDTO doctorDTO) {

        return requestSpecification()
                .body(doctorDTO)
                .post(DoctorRestController.API_DOCTOR_NEW)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(DoctorDTO.class);
    }


}
