   package moj.project.integration.support;

   import com.github.tomakehurst.wiremock.WireMockServer;
   import org.springframework.http.HttpHeaders;
   import org.springframework.http.MediaType;

   import static com.github.tomakehurst.wiremock.client.WireMock.*;

public interface WiremockTestSupport {

     default void stubForSections(WireMockServer wireMockServer) {
        wireMockServer.stubFor(
                get(urlPathEqualTo("/sections"))
                        .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/stat-sections.json")
                        ));

    }


    default void stubForBenefit1(WireMockServer wireMockServer) {

                wireMockServer.stubFor(
                        get(urlEqualTo("/benefits?benefit=kwal&section=K%20-%20Choroby%20uk%C5%82adu%20dokrewnego&catalog=1a&page=1&limit=10&format=json&api-version=1.1"))
                                .willReturn(aResponse()
                                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                .withBodyFile("wiremock/stat-benefits.json")));
    }

}

