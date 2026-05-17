package com.devtoys.observabilidadelab.model;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Simple Order model used for observability lab examples.
 */
public class Order {

	private Long id;
	private String customerName;
	private String status;
	private BigDecimal amount;
	private LocalDateTime createdAt;

	public Order() {
	}

	public Order(Long id, String customerName, String status, BigDecimal amount, LocalDateTime createdAt) {
		this.id = id;
		this.customerName = customerName;
		this.status = status;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", customerName='" + customerName + '\'' +
				", status='" + status + '\'' +
				", amount=" + amount +
				", createdAt=" + createdAt +
				'}';
	}
}
