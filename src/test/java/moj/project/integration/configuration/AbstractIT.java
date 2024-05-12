package moj.project.integration.configuration;

import moj.project.MedicalRegistrationApplication;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Import(PersistenceContainerTestConfiguration.class)
@SpringBootTest(
        classes = MedicalRegistrationApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)

public class AbstractIT {
    @LocalServerPort
    protected int port;
    @Value("${server.servlet.context-path}")
    protected String basePath;
    @Autowired
  Flyway flyway;

    @AfterEach
    void clean(){
    flyway.clean();
        flyway.migrate();
    }

}
