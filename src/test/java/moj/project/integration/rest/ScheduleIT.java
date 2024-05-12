package moj.project.integration.rest;

import jakarta.validation.constraints.Negative;
import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.DoctorScheduleDTO;
import moj.project.api.dto.ScheduleDTO;
import moj.project.integration.configuration.RestAssuredIntegrationTestBase;
import moj.project.integration.support.ScheduleControllerTestSupport;
import moj.project.util.DtoFixtures;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScheduleIT extends RestAssuredIntegrationTestBase
implements ScheduleControllerTestSupport {
    @Test
    void thatScheduleAddTimeSlotCorrectly(){
        //given
        String existingDoctorEmail = DtoFixtures.someDoctor0_3().getExistingDoctorEmail();
        String existingDoctorCode = DtoFixtures.someDoctor0_3().getDoctorCode();
        ScheduleDTO schedule = DtoFixtures.someScheduleTimeSlot1();
        //when
        DoctorScheduleDTO doctorScheduleBeforeAdding = showScheduleTimeSlots(existingDoctorEmail);
        ScheduleDTO scheduleSaved = addScheduleTimeSlot(existingDoctorCode,schedule);
        DoctorScheduleDTO doctorScheduleAfterAdding = showScheduleTimeSlots(existingDoctorEmail);

        //then
     var scheduleTimeSlotsBeforeAdding = new ArrayList<>(doctorScheduleBeforeAdding.getSchedules());
     var scheduleTimeSlotsAfterAdding = new ArrayList<>(doctorScheduleAfterAdding.getSchedules());
         scheduleTimeSlotsAfterAdding.removeAll(scheduleTimeSlotsBeforeAdding);
        assertThat(scheduleTimeSlotsAfterAdding.get(0).getScheduleCode()).isEqualTo(scheduleSaved.getScheduleCode());
    }
    @Test
    void thatRetrievingDoctorScheduleWorksCorrectly(){
        //given
        DoctorDTO doctorDTO = DtoFixtures.someDoctor0_3();
        String existingDoctorEmail =doctorDTO.getExistingDoctorEmail();
        //when
        DoctorScheduleDTO doctorSchedule = showScheduleTimeSlots(existingDoctorEmail);
        //then
        assertThat(doctorSchedule.getDoctorCode()).isEqualTo(doctorDTO.getDoctorCode());
        assertThat(doctorSchedule.getDoctorEmail()).isEqualTo(doctorDTO.getExistingDoctorEmail());
        assertThat(doctorSchedule.getDoctorNameSurname())
                .isEqualTo(doctorDTO.getName() + " " + doctorDTO.getSurname());
        assertThat(doctorSchedule.getSchedules().size()).isEqualTo(2);
        assertThat(doctorSchedule.getSchedules()).contains(DtoFixtures.someScheduleTimeSlotDoctorSchedule0_1());
        assertThat(doctorSchedule.getSchedules()).contains(DtoFixtures.someScheduleTimeSlotDoctorSchedule0_2());
    }
    @Negative
    @Test
    void thatRetrievingDoctorScheduleThrowsException(){
        //given
         String notExistingDoctorEmail = "jozef_dentysta@lekaz.pl";
        //when//then

                showScheduleTimeSlotsWithException(notExistingDoctorEmail);


    }
    @Test
    void thatUpdatingScheduleTimeSlotWorksCorrectly(){
        //given
        ScheduleDTO schedule = DtoFixtures.someUpdatedScheduleTimeSlot0_1();
        //when
        ScheduleDTO scheduleUpdated = updateScheduleTimeSlot(schedule);
        //then
        assertThat(scheduleUpdated)
                .isEqualTo(DtoFixtures.someUpdatedScheduleTimeSlot0_2());
    }
    @Test
    void thatDeletingScheduleTimeSlotWorksCorrectly(){
        //given
        String scheduleCode = DtoFixtures.someScheduleTimeSlotDoctorSchedule0_1().getScheduleCode();
        String existingDoctorEmail = DtoFixtures.someDoctor0_3().getExistingDoctorEmail();
        //when
        List<DoctorScheduleDTO.ScheduleDTO> schedulesBeforeDeleting = showScheduleTimeSlots(existingDoctorEmail).getSchedules();
        List<DoctorScheduleDTO.ScheduleDTO> schedulesAfterDeleting = deleteScheduleTimeSlot(scheduleCode, existingDoctorEmail).getSchedules();
        schedulesBeforeDeleting.removeAll(schedulesAfterDeleting);
        //then
        assertThat(schedulesBeforeDeleting.size()).isEqualTo(1);
        assertThat(schedulesBeforeDeleting.get(0).getScheduleCode()).isEqualTo(scheduleCode);


    }
}
