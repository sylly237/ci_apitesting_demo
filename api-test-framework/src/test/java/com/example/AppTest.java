package com.example;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;

public class AppTest {

    private static WireMockServer wireMockServer;

    @BeforeAll
    static void setup() {
        wireMockServer = new WireMockServer(9090);
        wireMockServer.start();

        configureFor("localhost", 9090);
    }

    @AfterAll
    static void teardown() {
        wireMockServer.stop();
    }

    @Test
    public void shouldGetUserSuccessfully() {

        stubFor(get(urlEqualTo("/users/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                      "id": 1,
                      "name": "John Doe"
                    }
                """)));

        given()
            .baseUri("http://localhost:9090")
        .when()
            .get("/users/1")
        .then()
            .statusCode(200)
            .body("name", org.hamcrest.Matchers.equalTo("John Doe"));
    }
}
