package com.devtoys.observabilidadelab.service;

import com.devtoys.observabilidadelab.client.ExternalPaymentClient;
import com.devtoys.observabilidadelab.exception.OrderNotFoundException;
import com.devtoys.observabilidadelab.model.Order;
import com.devtoys.observabilidadelab.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderService {

	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	private final OrderRepository repository;
	private final ExternalPaymentClient externalPaymentClient;
	private final Random random = new Random();

	public OrderService(OrderRepository repository, ExternalPaymentClient externalPaymentClient) {
		this.repository = repository;
		this.externalPaymentClient = externalPaymentClient;
	}

	public List<Order> getAllOrders() {
		log.info("Service: getAllOrders start");
		List<Order> result = repository.findAll();
		log.info("Service: getAllOrders end, found {} orders", result.size());
		return result;
	}

	public Order getOrderById(Long id) {
		log.info("Service: getOrderById {} start", id);
		return repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
	}

	public Order getSlowOrderById(Long id) {
		log.info("Service: getSlowOrderById {} start", id);
		Optional<Order> o = repository.simulateSlowFindById(id, 1500);
		log.info("Service: getSlowOrderById {} end", id);
		return o.orElseThrow(() -> new OrderNotFoundException(id));
	}

	public Order getOrderWithError(Long id) {
		log.info("Service: getOrderWithError {} start", id);
		throw new RuntimeException("Simulated error for order " + id);
	}

	/**
	 * Random scenario: 0 -> fast, 1 -> slow, 2 -> error
	 */
	public Order getRandomScenario() {
		int pick = random.nextInt(3);
		log.info("Service: getRandomScenario picked {}", pick);
		long id = 1L;
		switch (pick) {
			case 0:
				return getOrderById(id);
			case 1:
				return getSlowOrderById(id);
			default:
				return getOrderWithError(id);
		}
	}

	public Order getOrderWithExternalCall(Long id) {
		log.info("Service: getOrderWithExternalCall {} start", id);
		Order order = repository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
		try {
			// call external endpoint to generate an external span
			externalPaymentClient.callDelayEndpoint(1);
		} catch (Exception e) {
			log.warn("External call failed: {}", e.getMessage());
		}
		log.info("Service: getOrderWithExternalCall {} end", id);
		return order;
	}
}
