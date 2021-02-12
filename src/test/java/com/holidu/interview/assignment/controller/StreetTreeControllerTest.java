package com.holidu.interview.assignment.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.holidu.interview.assignment.App;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration test for /street-tree/in-circle-area REST endpoint.
 * @author Vadim Babich
 */
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)

@TestPropertySource(properties = {
        "street.tree.base-url=http://localhost:8765",
        "street.tree.resource-id=test"
})
public class StreetTreeControllerTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(8765));


    @Autowired
    private MockMvc underTestMockMvc;

    private String thirdPartyServerResponseBodyWithRectangleArea;

    @Before
    public void setUp() throws IOException {
        thirdPartyServerResponseBodyWithRectangleArea = Files
                .readString(Paths.get("src/test/resources/within_rect.json"));
    }


    @Test
    public void shouldResponseStatusOkAndCircleShapeAggregatedResultWhenGivenThirdPartyResponseWithRectangleArea()
            throws Exception {

        givenThat(WireMock.get(urlPathEqualTo("/resource/test"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(thirdPartyServerResponseBodyWithRectangleArea)));

        underTestMockMvc.perform(get("/street-tree/in-circle-area")
                .param("radius", "50")
                .param("x", "-122.334540")
                .param("y", "47.59815")

                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.['London planetree']").value(3))
                .andExpect(jsonPath("$.['red maple']").value(4))
                .andExpect(jsonPath("$.['American linden']").value(5));
    }

    @Test
    public void shouldResponseStatusBadRequestWhenGivenBadLatitudeValue() throws Exception {

        underTestMockMvc.perform(get("/street-tree/in-circle-area")
                .param("radius", "50")
                .param("x", "-122.334540")
                .param("y", "90.1")

                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

                .andExpect(jsonPath("$.message")
                        .value("Latitude 90.100000 cannot be less than -90 or more than 90"));
    }

}