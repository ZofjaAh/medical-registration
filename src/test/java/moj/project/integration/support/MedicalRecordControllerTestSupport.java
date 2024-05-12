package moj.project.integration.support;

import io.restassured.specification.RequestSpecification;
import moj.project.api.controller.rest.MedicalRecordRestController;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.MedicalRecordDTO;
import org.springframework.http.HttpStatus;

public interface MedicalRecordControllerTestSupport {
    RequestSpecification requestSpecification();
    default AppointmentInformationDTO addMedicalRecord(final String appointmentCode,
            final MedicalRecordDTO medicalRecordDTO) {

        return requestSpecification()
                .pathParam("appointmentCode", appointmentCode)
                .body(medicalRecordDTO)
                .post(MedicalRecordRestController.API_RECORD + MedicalRecordRestController.ADD_MEDICAL_RECORD)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(AppointmentInformationDTO.class);
    }


}
