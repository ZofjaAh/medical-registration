package moj.project.integration.rest;

import moj.project.api.dto.HealthPatientHistoryDTO;
import moj.project.integration.configuration.RestAssuredIntegrationTestBase;
import moj.project.integration.support.PatientHistoryControllerTestSupport;
import moj.project.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientHistoryIT extends RestAssuredIntegrationTestBase
implements PatientHistoryControllerTestSupport {
    @Test
    void thatRetrievingPatientHistoryWorksCorrectly(){
        //given
        String existingPatientPesel = "52070997836";
        //when
        HealthPatientHistoryDTO result = showPatientHealthHistory(existingPatientPesel);
        //then
        assertThat(result.getPatientPesel()).isEqualTo(existingPatientPesel);
        assertThat(result.getAppointments().size()).isEqualTo(2);
        assertThat(result.getAppointments())
               .contains(DtoFixtures.someHealthPatientAppointment0_1());
        assertThat(result.getAppointments())
               .contains(DtoFixtures.someHealthPatientAppointment0_2());
         }
}
