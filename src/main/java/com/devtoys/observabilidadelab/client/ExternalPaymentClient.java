package com.devtoys.observabilidadelab.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Simple external client to simulate an HTTP dependency.
 */
@Component
public class ExternalPaymentClient {

	private static final Logger log = LoggerFactory.getLogger(ExternalPaymentClient.class);

	private final RestTemplate restTemplate;

	public ExternalPaymentClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String callDelayEndpoint(int seconds) {
		String url = "https://httpbin.org/delay/" + seconds;
		log.info("Calling external endpoint: {}", url);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		log.info("External call completed with status: {}", response.getStatusCodeValue());
		return response.getBody();
	}

	public int callStatusEndpoint(int status) {
		String url = "https://httpbin.org/status/" + status;
		log.info("Calling external status endpoint: {}", url);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		log.info("External call completed with status: {}", response.getStatusCodeValue());
		return response.getStatusCodeValue();
	}
}
