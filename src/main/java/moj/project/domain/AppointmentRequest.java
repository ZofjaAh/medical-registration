package moj.project.domain;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@With
@Builder
public class AppointmentRequest {

    String existingPatientEmail;

    String patientName;
    String patientSurname;
    String patientPesel;
    String patientGender;
    Integer patientAge;
    String patientPhone;
    String patientEmail;
    String patientAddressCountry;
    String patientAddressCity;
    String patientAddressPostalCode;
    String patientAddressStreet;
    String appointmentCode;
    String commentPatient;
    Boolean execution;
    String scheduleCode;


}
