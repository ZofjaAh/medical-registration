package moj.project.api.controller.rest.mockito;

import moj.project.api.controller.rest.PatientHistoryRestController;
import moj.project.api.dto.HealthPatientHistoryDTO;
import moj.project.api.dto.mapper.PatientMapper;
import moj.project.businnes.PatientService;
import moj.project.domain.HealthPatientHistory;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientHistoryRestControllerMockitoTest {
    @Mock
    private PatientService patientService;
    @Mock
    private PatientMapper patientMapper;
    @InjectMocks
    private PatientHistoryRestController patientHistoryRestController;
    @Test
    void thatRetrievingHealthPatientsHistoryWorksCorrectly(){
        //given
      String  patientPesel = "98012132434";
        HealthPatientHistory healthPatientHistory =  DomainFixtures.someHealthPatientHistory1();
      //when
        when(patientService.findHealthPatientHistoryByPatientPesel(patientPesel)).thenReturn(healthPatientHistory);
        when(patientMapper.map(healthPatientHistory)).thenReturn(DtoFixtures.someHealthPatientHistory1());
        //then
        HealthPatientHistoryDTO result = patientHistoryRestController.showPatientHealthHistory(patientPesel);
        Assertions.assertThat(result).isEqualTo(DtoFixtures.someHealthPatientHistory1());
    }

}