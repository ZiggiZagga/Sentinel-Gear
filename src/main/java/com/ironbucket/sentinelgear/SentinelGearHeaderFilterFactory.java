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
import com.ironbucket.pactumscroll.StorageRequestContract;
import com.ironbucket.pactumscroll.TokenUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Validated
class SentinelGearHeaderFilterFactory extends AbstractGatewayFilterFactory<SentinelGearHeaderFilterFactory.Config> {

	private ObjectMapper mapper;


	@Value("${ironbucket.security.secret}")
	private String SIG_K;
	public SentinelGearHeaderFilterFactory() {
		super(Config.class);
		this.mapper = new ObjectMapper();
	}

	@Override
	public GatewayFilter apply(Config config) {		
		return (exchange, chain)->{


			ServerHttpRequest mutatedRequest = exchange.getRequest();
			try {
				String correlationId =UUID.randomUUID().toString();
				StorageRequestContract storageRequestInfo = TokenUtils.extractFromRequestPath(exchange);	
				log.info("request ID: "+correlationId+", path: "+storageRequestInfo.path());
				String payload = mapper.writeValueAsString(storageRequestInfo);
				String payloadPretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(storageRequestInfo);
				String signature = TokenUtils.signPayload(payload, SIG_K, mapper);

				if((signature+"").length() > 35) {
					String timestamp = String.valueOf(Instant.now().toEpochMilli());
					String info =  String.format("""
							%6$s: %1$s
							%5$s: %2$s
							X-Timestamp: %3$s
							X-Payload: %4$s
							"""
							,correlationId
							,signature
							,timestamp
							,payloadPretty
							,TokenUtils.SIGNATURE_HEADER_NAME
							,TokenUtils.CORRELATION_ID_HEADER_NAME);

					log.debug(info);
					mutatedRequest = mutatedRequest
							.mutate()
							.header(TokenUtils.CORRELATION_ID_HEADER_NAME, correlationId)
							.header(TokenUtils.SIGNATURE_HEADER_NAME, signature)
							.header(TokenUtils.TIMESTAMP_HEADER_NAME, timestamp)
							.build();
				}else {
					log.error("Illegal signature");
				}	
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return chain.filter(exchange.mutate().request(mutatedRequest).build());
		};
	}

	@Override
	public String name() {
		return "SentinelGearHeader";
	}

	record Config() {};
}