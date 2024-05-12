package moj.project.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import moj.project.MedicalRegistrationApplication;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfiguration {
    @Bean
    public GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi.builder()
                .group("default")
                .pathsToMatch("/**")
                .packagesToScan(MedicalRegistrationApplication.class.getPackageName())
                .build();
    }
    @Bean
    public OpenAPI springDocOpenApi(){
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Online medical registration application")
                        .version("1.0"));
    }

}
