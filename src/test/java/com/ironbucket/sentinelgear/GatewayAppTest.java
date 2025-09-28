package com.ironbucket.sentinelgear;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = EdgeGatewayApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GatewayAppTest {
    @Autowired
    private ApplicationContext context;

    @LocalServerPort
    private int port;

    private WebTestClient webTestClient;

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        this.webTestClient = WebTestClient.bindToServer().baseUrl("http://localhost:" + port).build();
    }

    @Test
    void contextLoads() {
        assertNotNull(context);
    }

    @Test
    void gatewayRoutesConfigured() {
        // Intention: Gateway must route traffic (expect 404 or 401 for unknown route, but not 5xx)
        webTestClient.get().uri("/some-nonexistent-route").exchange()
                .expectStatus().value(status -> assertTrue(status == 404 || status == 401));
    }

    @Test
    void securityIsEnabled() {
        // Intention: Gateway must enforce security policies (should not allow open access)
        webTestClient.get().uri("/").exchange()
                .expectStatus().value(status -> assertTrue(status == 401 || status == 403));
    }
}
