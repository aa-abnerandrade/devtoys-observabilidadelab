package com.devtoys.observabilidadelab.repository;

import com.devtoys.observabilidadelab.model.Order;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple in-memory repository used for demonstration and testing of observability.
 */
@Repository
public class OrderRepository {

	private static final Logger log = LoggerFactory.getLogger(OrderRepository.class);

	private final Map<Long, Order> storage = new ConcurrentHashMap<>();

	public OrderRepository() {
		// initialize with some sample orders
		storage.put(1L, new Order(1L, "Alice", "CREATED", new BigDecimal("19.99"), LocalDateTime.now().minusDays(1)));
		storage.put(2L, new Order(2L, "Bob", "PROCESSING", new BigDecimal("49.50"), LocalDateTime.now().minusHours(5)));
		storage.put(3L, new Order(3L, "Carol", "COMPLETED", new BigDecimal("5.00"), LocalDateTime.now().minusDays(2)));
	}

	public Optional<Order> findById(Long id) {
		log.debug("Repository: findById {}", id);
		return Optional.ofNullable(storage.get(id));
	}

	public List<Order> findAll() {
		log.debug("Repository: findAll");
		return new ArrayList<>(storage.values());
	}

	/**
	 * Simulate a slow DB call by sleeping for the provided milliseconds.
	 */
	public Optional<Order> simulateSlowFindById(Long id, long sleepMillis) {
		log.debug("Repository: simulateSlowFindById {} (sleep {}ms)", id, sleepMillis);
		try {
			Thread.sleep(sleepMillis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return findById(id);
	}

	/**
	 * Default slow find with 1 second delay.
	 */
	public Optional<Order> simulateSlowFindById(Long id) {
		return simulateSlowFindById(id, 1000);
	}
}
