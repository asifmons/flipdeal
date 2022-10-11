package com.coverself.flipdeal.entity;

import java.time.LocalDate;
import java.util.HashMap;

import lombok.Data;

@Data
public class ExchangeRates {
	private boolean success;
	private String base;
	private LocalDate date;
	private Long timestamp;
	public HashMap<String, Double> rates;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public HashMap<String, Double> getRates() {
		return rates;
	}

	public void setRates(HashMap<String, Double> rates) {
		this.rates = rates;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

}
