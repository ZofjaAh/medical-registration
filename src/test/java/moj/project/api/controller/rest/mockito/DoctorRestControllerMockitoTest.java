package moj.project.api.controller.rest.mockito;

import moj.project.api.controller.rest.DoctorRestController;
import moj.project.api.dto.DoctorDTO;
import moj.project.api.dto.mapper.DoctorMapper;
import moj.project.businnes.DoctorService;
import moj.project.domain.Doctor;
import moj.project.util.DomainFixtures;
import moj.project.util.DtoFixtures;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorRestControllerMockitoTest {
    @Mock
    private DoctorService doctorService;
    @Mock
    private DoctorMapper doctorMapper;
    @InjectMocks
    private DoctorRestController doctorRestController;
    @Test
    void thatCreatingDoctorWorksCorrectly(){
        //given
        String doctorCode = "i8j5hs90-j593-547d-djg4-lk84db7987k6";
         String email = "borys_zawalny88@gmail.pl";
        //when
        when(doctorMapper.map(Mockito.any(DoctorDTO.class))).thenReturn(DomainFixtures.someDoctor1());
        when(doctorService.create(Mockito.any(Doctor.class))).thenReturn(DomainFixtures.someDoctor1().withDoctorCode(doctorCode));
        when(doctorMapper.map(Mockito.any(Doctor.class))).thenReturn(DtoFixtures.someDoctor2().withExistingDoctorEmail(email));
        //then
        DoctorDTO result = doctorRestController.addDoctor(DtoFixtures.someDoctor2().withEmail(email));
        Assertions.assertThat(result.getEmail()).isNull();
        Assertions.assertThat(result.getExistingDoctorEmail()).isEqualTo(email);
    }

}