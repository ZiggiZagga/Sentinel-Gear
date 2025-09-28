package com.ironbucket.sentinelgear;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.ironbucket.pactumscroll.StorageRequestContract;
// import com.ironbucket.pactumscroll.TokenUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Validated
public class SentinelGearHeaderFilterFactory extends AbstractGatewayFilterFactory<SentinelGearHeaderFilterFactory.Config> {

	private ObjectMapper mapper;


	@Value("${ironbucket.security.secret}")
	private String SIG_K;
	public SentinelGearHeaderFilterFactory() {
		super(Config.class);
		this.mapper = new ObjectMapper();
	}

	@Override
       public GatewayFilter apply(Config config) {
	       return (exchange, chain) -> {
		       ServerHttpRequest mutatedRequest = exchange.getRequest();
		       // MOCK: Pactumscroll logic is missing, so we only add a correlation header for now
		       String correlationId = UUID.randomUUID().toString();
		       mutatedRequest = mutatedRequest
			       .mutate()
			       .header("X-Correlation-Id", correlationId)
			       .build();
		       return chain.filter(exchange.mutate().request(mutatedRequest).build());
	       };
       }

	@Override
	public String name() {
		return "SentinelGearHeader";
	}

	public static record Config() {};
}