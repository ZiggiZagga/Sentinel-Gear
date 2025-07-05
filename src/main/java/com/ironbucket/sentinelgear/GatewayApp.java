package com.ironbucket.sentinelgear;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({			
			"com.ironbucket.pactumscroll",
			"com.ironbucket.sentinelgear"
})
@SpringBootApplication
@EnableDiscoveryClient
class GatewayApp {
	public static void main(String[] args) {
		SpringApplication.run(GatewayApp.class, args);
	}
}
