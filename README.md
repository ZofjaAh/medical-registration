### Project Description:
Medical-Registration is the project that allows online registration for a doctor's appointment.

- The Application allows doctors to register in the system and creating their personal schedule with available time slots.
- In addition, they can change or delete un-booked visits. 
- Doctors can add a medical record after the visit and check the patient's medical history.
- The patient can request all available doctors, book an appointment or cancel the visit and view all active and inactive doctor visits.
- The patient can check what the doctor wrote in the medical-record after the appointment.

Medical-Registration created with using: 
- Java 17,
- Spring Boot,
- Gradle,
- Lombok,
- Flyway,
- Tomcat,
- Docker.
It interacts with database(PostgreSQL) using Spring Data JPA and Hibernate. 

The Application contains layers WEB and REST (with authorization DOCTOR/PATIENT or REST_API respectively).
WEB layer created with using Thymeleaf based on Template Mode: HTML with input validation. 
REST-service establishes a connection with OpenApi https://api.nfz.gov.pl/app-stat-api-jgp that allows according to a category of the disease determine the method of treatment.
Test's part contains such tests as:
* DataJpaTests, 
* WebMvcTest,
* SpringBootTests,
* RestAssured tests,
* WireMock tests
