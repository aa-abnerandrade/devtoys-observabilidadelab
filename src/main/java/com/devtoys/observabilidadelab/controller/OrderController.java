package com.devtoys.observabilidadelab.controller;

import com.devtoys.observabilidadelab.model.Order;
import com.devtoys.observabilidadelab.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}

	@GetMapping("/health-demo")
	public ResponseEntity<String> healthDemo() {
		log.info("health-demo requested");
		return ResponseEntity.ok("OK - orders observability lab");
	}

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getAll() {
		return ResponseEntity.ok(service.getAllOrders());
	}

	@GetMapping("/orders/{id}")
	public ResponseEntity<Order> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.getOrderById(id));
	}

	@GetMapping("/orders/{id}/slow")
	public ResponseEntity<Order> getSlow(@PathVariable Long id) {
		return ResponseEntity.ok(service.getSlowOrderById(id));
	}

	@GetMapping("/orders/{id}/error")
	public ResponseEntity<Order> getError(@PathVariable Long id) {
		return ResponseEntity.ok(service.getOrderWithError(id));
	}

	@GetMapping("/orders/{id}/external")
	public ResponseEntity<Order> getWithExternal(@PathVariable Long id) {
		return ResponseEntity.ok(service.getOrderWithExternalCall(id));
	}

	@GetMapping("/orders/scenario/random")
	public ResponseEntity<Order> randomScenario() {
		return ResponseEntity.ok(service.getRandomScenario());
	}
}
