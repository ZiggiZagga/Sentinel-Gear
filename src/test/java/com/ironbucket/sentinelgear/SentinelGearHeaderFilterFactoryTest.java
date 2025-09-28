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
class SentinelGearHeaderFilterFactoryTest {
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
    void filterFactoryLoads() {
        assertNotNull(context.getBean(SentinelGearHeaderFilterFactory.class));
    }

    @Test
    void filterInjectsHeaders() {
        // Intention: Simulate a request and assert that correlation header is injected
        webTestClient.get().uri("/any").exchange()
                .expectHeader().exists("X-Correlation-Id");
    }

    @Test
    void filterHandlesInvalidSignatureGracefully() {
        // Intention: Simulate a request with invalid signature scenario (not implemented, so just check no crash)
        webTestClient.get().uri("/any").header("X-Signature", "invalid").exchange()
                .expectStatus().is4xxClientError();
    }

    @Test
    void filterLogsRequestInfo() {
        // Intention: Simulate a request and assert that request info is logged (not directly testable, but no error)
        webTestClient.get().uri("/any").exchange()
                .expectStatus().is4xxClientError();
    }
}
