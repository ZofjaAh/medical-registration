package moj.project.integration.rest;

import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.ScheduleDTO;
import moj.project.integration.configuration.RestAssuredIntegrationTestBase;
import moj.project.integration.support.SearchControllerTestSupport;
import moj.project.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchIT extends RestAssuredIntegrationTestBase
implements SearchControllerTestSupport {
    @Test
    void thatRetrievingAvailableDoctorsWorksCorrectly(){
        //given //when
        List<DoctorDTO> existingDoctors = showDoctors().getDoctors();
        //then
        assertThat(existingDoctors.size()).isEqualTo(2);
        assertThat(existingDoctors).contains(DtoFixtures.someDoctor0_2());
        assertThat(existingDoctors).contains(DtoFixtures.someDoctor0_3());

    }
    @Test
    void thatRetrievingDoctorsAvailableTimeSlotsWorksCorrectly(){
        //given
        String existingDoctorCode = DtoFixtures.someDoctor0_2().getDoctorCode();
        //when
        List<ScheduleDTO> schedules = showAvailableTimeSlots(existingDoctorCode).getSchedules();
        //then
        assertThat(schedules.size()).isEqualTo(2);
        assertThat(schedules).contains(DtoFixtures.someScheduleTimeSlot0_1());
        assertThat(schedules).contains(DtoFixtures.someScheduleTimeSlot0_2());
    }
}
