package moj.project.api.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import moj.project.api.dto.StatBenefitDTO;
import moj.project.api.dto.StatBenefitsDTO;
import moj.project.api.dto.StatSectionDTO;
import moj.project.api.dto.StatSectionsDTO;
import moj.project.api.dto.mapper.StatHealthServiceMapper;
import moj.project.businnes.StatHealthServiceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(StatHealthServiceRestController.API_STAT)
public class StatHealthServiceRestController {
    public static final String API_STAT = "/api/stat";
    public static final String SHOW_SECTIONS = "/sections";
    public static final String SHOW_BENEFITS = "/benefits";
    private final StatHealthServiceMapper statHealthServiceMapper;
    private final StatHealthServiceService statHealthServiceService;


    @Operation(summary = "Get Medical Benefits")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Medical benefits found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StatBenefitsDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Invalid data provided", content = @Content),
            @ApiResponse(responseCode = "404", description = "Medical benefits not found", content = @Content)
    })

    @GetMapping(value = SHOW_BENEFITS)
    public StatBenefitsDTO getMedicalBenefits(
            @Parameter(description = "kwal/lecz/diagn/bad/chem/hosp")
            @RequestParam(value = "benefit")
            final String benefit,

            @Parameter(description = "1a/1b/1c/1d")
            @RequestParam(value = "catalog")
            final String catalog,

            @Parameter(description = "Description of the Uniform Groups Section of Patients")
            @RequestParam(value = "section")
            final String section,

            @Parameter(description = "Number of page")
            @RequestParam(value = "page")
            final Integer page
    ) {
        return StatBenefitsDTO.builder()
                .benefits(getAvailableBenefits(benefit, catalog, section, page))
                .build();
    }
    @Operation(summary = "Get Sections of Patients' Uniform Groups ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patients' sections found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = StatSectionsDTO.class))
            }),
             @ApiResponse(responseCode = "404", description = "Patients' sections not found", content = @Content)
    })
    @GetMapping(value = SHOW_SECTIONS)
    public StatSectionsDTO getSections() {

        return StatSectionsDTO.builder().sections(getAvailableSections()).build();
    }

    private List<StatSectionDTO> getAvailableSections() {
        return statHealthServiceService.getSections()
                .stream().map(statHealthServiceMapper::map).toList();
    }

    private List<StatBenefitDTO> getAvailableBenefits(String benefit, String catalog, String section, int page) {

        return statHealthServiceService.getBenefits(benefit, catalog, section, page)
                .stream().map(statHealthServiceMapper::map).toList();
    }
}
