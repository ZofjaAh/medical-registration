package moj.project.integration.support;

import io.restassured.specification.RequestSpecification;
import moj.project.api.controller.rest.StatHealthServiceRestController;
import moj.project.api.dto.StatBenefitsDTO;
import moj.project.api.dto.StatSectionsDTO;
import org.springframework.http.HttpStatus;

import java.util.Map;

public interface StatHealthServiceControllerTestSupport {
    RequestSpecification requestSpecification();

    default StatBenefitsDTO getMedicalBenefits(final String benefit, final String catalog,final String section,final Integer page){
        Map<String,?> params = Map.of(
                "benefit", benefit ,
                "catalog", catalog,
                "section", section,
                "page", page);
        return requestSpecification()
                .params(params)
                .get(StatHealthServiceRestController.API_STAT + StatHealthServiceRestController.SHOW_BENEFITS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(StatBenefitsDTO.class);

    }
    default StatSectionsDTO getSections(){

        return requestSpecification()
                .get(StatHealthServiceRestController.API_STAT + StatHealthServiceRestController.SHOW_SECTIONS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(StatSectionsDTO.class);

    }
}
