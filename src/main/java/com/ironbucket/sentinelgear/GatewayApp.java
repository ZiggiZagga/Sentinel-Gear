package com.ironbucket.sentinelgear;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@EnableDiscoveryClient
@Controller
@RestController
@Configuration
public class GatewayApp {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApp.class, args);
	}
	@GetMapping(path="/")
	public  Mono<String> hello(@AuthenticationPrincipal Jwt principal) {	
		String user = "UNKNOWN";
		if(principal != null) {
			user = principal.getClaimAsString("preferred_username");
		}
        return Mono.just("Hello "+user);
	}

}
