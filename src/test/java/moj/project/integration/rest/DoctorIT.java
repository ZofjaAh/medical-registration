package moj.project.integration.rest;

import moj.project.api.dto.DoctorDTO;
import moj.project.integration.configuration.RestAssuredIntegrationTestBase;
import moj.project.integration.support.DoctorControllerTestSupport;
import moj.project.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DoctorIT extends RestAssuredIntegrationTestBase
implements DoctorControllerTestSupport {
    @Test
    void thatCreatingDoctorWorksCorrectly(){
        //given
        DoctorDTO doctor = DtoFixtures.someDoctor0_1();
        //when
        DoctorDTO doctorSaved = addDoctor(doctor);
        //then
        assertThat(doctorSaved)
                .usingRecursiveComparison()
                .ignoringFields("doctorCode", "existingDoctorEmail", "email")
                .isEqualTo(doctor);
        assertThat(doctorSaved.getExistingDoctorEmail()).isEqualTo(doctor.getEmail());
    }
}
