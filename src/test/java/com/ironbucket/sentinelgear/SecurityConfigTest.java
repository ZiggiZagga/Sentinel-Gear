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
class SecurityConfigTest {
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
    void securityConfigLoads() {
        assertNotNull(context.getBean(SecurityConfig.class));
    }

    @Test
    void oidcProviderConfigured() {
        // Intention: OIDC provider should be present in the context (mocked for test)
        assertTrue(context.getBeansOfType(org.springframework.security.oauth2.client.registration.ClientRegistrationRepository.class).size() > 0,
                "OIDC provider should be configured");
    }

    @Test
    void unauthorizedAccessIsDenied() {
        // Intention: Unauthorized requests should be denied (401/403)
        webTestClient.get().uri("/").exchange()
                .expectStatus().value(status -> assertTrue(status == 401 || status == 403));
    }

    @Test
    void policiesAreEnforced() {
        // Intention: Policy-driven access (simulate request, expect denial or success based on policy)
        // For now, expect denial as default policy
        webTestClient.get().uri("/protected-resource").exchange()
                .expectStatus().value(status -> assertTrue(status == 401 || status == 403));
    }
}
