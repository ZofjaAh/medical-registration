package moj.project.integration.rest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import moj.project.api.dto.AppointmentInformationDTO;
import moj.project.api.dto.MedicalRecordDTO;
import moj.project.integration.configuration.RestAssuredIntegrationTestBase;
import moj.project.integration.support.MedicalRecordControllerTestSupport;
import moj.project.util.DtoFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class MedicalRecordIT extends RestAssuredIntegrationTestBase
implements MedicalRecordControllerTestSupport {

    @PersistenceContext
    private EntityManager entityManager;
    private void flushAndClear(){
        entityManager.flush();
        entityManager.clear();
    }
    @Test
    void thatCreatingMedicalRecordWorksCorrectly(){
        //given
        String existingAppointmentCode = "lo7km6l9-sl69-al4l-l2h7-jus74n59a7j3";
        MedicalRecordDTO medicalRecord = DtoFixtures.someMedicalRecord1();
        //when
        AppointmentInformationDTO result = addMedicalRecord(existingAppointmentCode, medicalRecord);
        //then
        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("medicalRecordCode")
                .isEqualTo(DtoFixtures.someAppointmentInformation0_3());
    }

}
