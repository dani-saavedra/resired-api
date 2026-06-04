package com.resired.api.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AdmGuardControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void setup() {
        restClient = RestClient.builder()
            .baseUrl("http://localhost:" + port)
            .build();
    }

    @Test
    void shouldInactiveGuardSuccessfully() {

        // Arrange
        String bearerToken = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJyb2wiOiJBRE1JTiIsIm5hbWUiOiJKb2huIERvZSIsImFkbWluIjp0cnVlLCJpYXQiOjE1MTYyMzkwMjIsImF1dGhvcml0aWVzIjpbIkFETUlOIl19.kr4ZxaJHR1aICvvPJlJnPfUKXZp_rzflxMoGyUgMOHLAzv09sUPVzXMPP4AJPNkCFTybYQlt5_aV9e1nvx2WDYQaeLENJF_rzIthPCbhR2Y13I15DzoqWeQ7XDDJ2SLzkfP5N37coajkfhtV43RZlWggYSZbwiGsqfJcWsbYUcrgh5CfOuKvQMsXEdf15tZRBRQgSNPZS4f_ohiZ4hj0yWmxVLXnHoh4bapSY35gf3zvV_b6fuanq19jJvUqbFRbwEo08UtJDCiRch3We9k1I8j3_DAXh6CF6dqd0ssBxupf6H1lGfdqbWhwOrB3W1JTS51eD9x2DckZLdleFv3rqA";

        Integer idUser = 123;
        // Act
        ResponseEntity<String> response = restClient
            .delete()
            .uri("/api/admin/guard/{id_user}", idUser)
            .header(HttpHeaders.AUTHORIZATION, bearerToken)
            .retrieve()
            .toEntity(String.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Guard inactive successfully", response.getBody());
    }
}
