package moj.project.integration.support;

import io.restassured.specification.RequestSpecification;
import moj.project.api.controller.rest.PatientHistoryRestController;
import moj.project.api.dto.HealthPatientHistoryDTO;
import org.springframework.http.HttpStatus;

public interface PatientHistoryControllerTestSupport {
    RequestSpecification requestSpecification();
    default HealthPatientHistoryDTO showPatientHealthHistory(final String patientPesel) {

        return requestSpecification()
                .pathParam("patientPesel", patientPesel)
                .get(PatientHistoryRestController.API_PATIENT_HISTORY)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(HealthPatientHistoryDTO.class);
    }


}
